package com.monitoring.dashboardserver.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.monitoring.dashboardserver.domain.MetricHistory;
import com.monitoring.dashboardserver.domain.Server;
import com.monitoring.dashboardserver.dto.RealtimeMetricsResponseDto;
import com.monitoring.dashboardserver.dto.SystemInfoResponseDto;
import com.monitoring.dashboardserver.repository.MetricHistoryRepository;
import com.monitoring.dashboardserver.repository.ServerRepository;

import lombok.RequiredArgsConstructor;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 만들어 준다 (의존성 주입)
public class DashboardService {
    // repository
    private final ServerRepository serverRepository;
    private final MetricHistoryRepository metricHistoryRepository;

    // oshi의 핵심 객체들을 생성한다
    private final SystemInfo systemInfo = new SystemInfo(); // Oshi 라이브러리 접근
    private final HardwareAbstractionLayer hardware = systemInfo.getHardware(); // CPU, 메모리, 디스크 등, 하드웨어에 대한 정보를 담는 객체
    private final CentralProcessor processor = hardware.getProcessor(); // CPU를 다루는 객체
    private long[] prevTicks = new long[CentralProcessor.TickType.values().length];

    @Transactional(readOnly = true)
    public SystemInfoResponseDto getSystemInfo(String serviceId) {
        Server server = serverRepository.findByServiceId(serviceId)
        // 못 찾는다면 아래 에러 발생
        .orElseThrow(() -> new IllegalArgumentException("해당 서버를 찾을 수 없음"));

        return SystemInfoResponseDto.builder()
        .serviceId(server.getServiceId())
        .serviceName(server.getServiceName())
        .os(server.getOs())
        .totalCpuCores(server.getTotalCpuCores())
        .totalMemoryBytes(server.getTotalMemoryBytes())
        .totalDiskBytes(server.getTotalDiskBytes())
        .build();
    }

    // 모든 목록을 조회하는 리스트
    @Transactional(readOnly = true)
    public List<SystemInfoResponseDto> getAllSystemInfos() {
        // DB에서 모든 Entity 목록 찾기
        List<Server> servers = serverRepository.findAll();

        // 찾아온 Entity 목록을 DTO 리스트에 넣어서 리턴한다.
        return servers.stream()
        .map(server -> SystemInfoResponseDto.builder()
        .serviceId(server.getServiceId())
        .serviceName(server.getServiceName())
        .os(server.getOs())
        .totalCpuCores(server.getTotalCpuCores())
        .totalMemoryBytes(server.getTotalMemoryBytes())
        .totalDiskBytes(server.getTotalDiskBytes())
        .build())
        .collect(Collectors.toList());
    }

    public RealtimeMetricsResponseDto getRealTimeMetrics(String serviceId) {
        // DB에 해당 ServiceId가 존재하는지 확인
        Server server = serverRepository.findByServiceId(serviceId)
        // 못찾으면 아래 에러 발생
        .orElseThrow(() -> new IllegalArgumentException("해당 서버를 찾을 수 없음"));

        // 1. 메모리 정보 가져오기
        GlobalMemory memory = hardware.getMemory();

        // 2. CPU 사용률 계산하기
        double cpuUeage = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        // 다음 계산을 위해 현재 측정값을 이전 값으로 저장한다.
        this.prevTicks = processor.getSystemCpuLoadTicks();
        
        // 3. 사용중인 메모리 계산
        long usedMemory = memory.getTotal() - memory.getAvailable();

        // 4. 디스크는 잠시 랜덤으로 두기
        // 실시간 메트릭을 흉내내기 위한 데이터 (랜덤 함수 사용)
        // double cpuUeage = ThreadLocalRandom.current().nextDouble(0, 100);
        // long usedMemory = ThreadLocalRandom.current().nextLong(0, server.getTotalMemoryBytes());
        long usedDisk = ThreadLocalRandom.current().nextLong(0, server.getTotalDiskBytes());

        return RealtimeMetricsResponseDto.builder()
        .serviceId(serviceId)
        .cpuUsagePercent(Math.round(cpuUeage * 100) / 100.0)
        .usedMemoryBytes(usedMemory)
        .usedDiskBytes(usedDisk)
        .build();
    }

    // 실시간 메트릭을 DB에 저장하는 메소드
    @Transactional
    public void saveRealTimeMetrics(String serviceId, RealtimeMetricsResponseDto metricsResponseDto){
        // 1. serviceId를 사용해 Server엔티티를 DB에서 가져온다
        Server server = serverRepository.findByServiceId(serviceId)
        .orElseThrow(() -> new IllegalArgumentException("해당 서버를 찾을 수 없음"));

        // 2. 받은 DTO와 찾아온 server 엔티티를 사용해 MetricHistory 엔티티를 생성한다.
        MetricHistory metricHistory = MetricHistory.builder()
        .server(server)
        .cpuUsagePercent(metricsResponseDto.getCpuUsagePercent())
        .usedDiskBytes(metricsResponseDto.getUsedDiskBytes())
        .usedMemoryBytes(metricsResponseDto.getUsedMemoryBytes())
        .build();

        // 3. MetricsHistoryRepository 호출하여 저장
        metricHistoryRepository.save(metricHistory);

    }



}
