package com.monitoring.dashboardserver.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseDto<T> {
    @Builder.Default
    private String timestamp = LocalDateTime.now().toString();
    private String code;
    private String message;
    private T data;
}
