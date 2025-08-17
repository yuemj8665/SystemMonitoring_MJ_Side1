package com.monitoring.dashboardserver.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 이 클래스가 데이터베이스 테이블과 연계
@Getter // 각 필드의 값을 가져오는 getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 비어있는 생성자 생성
public class Server {

    @Id
    private String serviceId;
    private String serviceName;
    private String os;
    private Integer totalCpuCores;
    private Long totalMemoryBytes;
    private Long totalDiskBytes;

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL)
    private List<MetricHistory> metricHistories = new ArrayList<>();

    @Builder
    public Server(String serviceId, String serviceName, String os, Integer totalCpuCores, Long totalMemoryBytes, Long totalDiskBytes) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.os = os;
        this.totalCpuCores = totalCpuCores;
        this.totalMemoryBytes = totalMemoryBytes;
        this.totalDiskBytes = totalDiskBytes;
    }
}
