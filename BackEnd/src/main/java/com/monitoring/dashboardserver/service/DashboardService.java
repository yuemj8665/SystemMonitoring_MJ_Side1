package com.monitoring.dashboardserver.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monitoring.dashboardserver.domain.Server;
import com.monitoring.dashboardserver.dto.RealtimeMetricsResponseDto;
import com.monitoring.dashboardserver.dto.SystemInfoResponseDto;
import com.monitoring.dashboardserver.repository.ServerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 만들어 준다 (의존성 주입)
@Transactional(readOnly = true) // 이 서비스의 메소드는 전부 읽기전용으로 한다.
public class DashboardService {
    // repository
    private final ServerRepository serverRepository;

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

        // 실시간 메트릭을 흉내내기 위한 데이터 (랜덤 함수 사용)
        double cpuUeage = ThreadLocalRandom.current().nextDouble(0, 100);
        long usedMemory = ThreadLocalRandom.current().nextLong(0, server.getTotalMemoryBytes());
        long usedDisk = ThreadLocalRandom.current().nextLong(0, server.getTotalDiskBytes());

        return RealtimeMetricsResponseDto.builder()
        .serviceId(serviceId)
        .cpuUsagePercent(cpuUeage)
        .usedMemoryBytes(usedMemory)
        .usedDiskBytes(usedDisk)
        .build();
    }
}
