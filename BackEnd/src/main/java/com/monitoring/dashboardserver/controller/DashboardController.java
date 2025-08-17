package com.monitoring.dashboardserver.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monitoring.dashboardserver.dto.CommonResponseDto;
import com.monitoring.dashboardserver.dto.RealtimeMetricsResponseDto;
import com.monitoring.dashboardserver.dto.SystemInfoResponseDto;
import com.monitoring.dashboardserver.service.DashboardService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/server")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/{serviceId}/info")
    public ResponseEntity<CommonResponseDto<SystemInfoResponseDto>> getSystemInfo(@PathVariable String serviceId) {
        SystemInfoResponseDto infoDto = dashboardService.getSystemInfo(serviceId);

        CommonResponseDto<SystemInfoResponseDto> response = CommonResponseDto.<SystemInfoResponseDto>builder()
            .code("200")
            .message("Success")
            .data(infoDto)
            .build();
        return ResponseEntity.ok(response);
    }    

    @GetMapping
    public ResponseEntity<CommonResponseDto<List<SystemInfoResponseDto>>> getAllSystemInfos() {
        // service에게 전체 호출 요청
        List<SystemInfoResponseDto> allSystemInfoList = dashboardService.getAllSystemInfos();

        CommonResponseDto<List<SystemInfoResponseDto>> response = CommonResponseDto.<List<SystemInfoResponseDto>>builder()
            .code("200")
            .message("Success")
            .data(allSystemInfoList)
            .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{serviceId}/metrics/realtime")
    public ResponseEntity<CommonResponseDto<RealtimeMetricsResponseDto>> getRealtimeMetrics(@PathVariable String serviceId) {
        RealtimeMetricsResponseDto dto = dashboardService.getRealTimeMetrics(serviceId);
        CommonResponseDto<RealtimeMetricsResponseDto> response = CommonResponseDto.<RealtimeMetricsResponseDto>builder()
        .code("200")
        .message("SUCCESS")
        .data(dto)
        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{serviceId}/metrics")
    public ResponseEntity<CommonResponseDto<Void>> saveMetrics (@PathVariable String serviceId, @RequestBody RealtimeMetricsResponseDto metricsResponseDto) {
        // 1. 서비스에게 데이터 저장을 요청
        dashboardService.saveRealTimeMetrics(serviceId, metricsResponseDto);        
        CommonResponseDto<Void> response = CommonResponseDto.<Void>builder()
        .code("201")
        .message("Success")
        .build(); 
        return ResponseEntity.status(201).body(response);
    }
    
}
