package com.monitoring.dashboardserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RealtimeMetricsResponseDto {
    private String serviceId;
    private Double cpuUsagePercent;
    private Long usedMemoryBytes;
    private Long usedDiskBytes;
}
