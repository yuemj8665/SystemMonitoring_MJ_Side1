package com.monitoring.dashboardserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfoResponseDto {
    private String serviceId;
    private String serviceName;
    private String os;
    private Integer totalCpuCores;
    private Long totalMemoryBytes;
    private Long totalDiskBytes;
}
