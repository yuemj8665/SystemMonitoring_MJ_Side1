package com.monitoring.dashboardserver.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MetricHistory {

    @Id // 고유 키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 ID를 자동으로 생성하고 관리한다.
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계설정. 
    @JoinColumn(name = "server_service_id") // 외래키 컬럼명 지정
    private Server server;
    private LocalDateTime createdTime;
    private Double cpuUsagePercent;
    private Long usedMemoryBytes;
    private Long usedDiskBytes;
    
    @Builder
    public MetricHistory(Server server, Double cpuUsagePercent, Long usedMemoryBytes, Long usedDiskBytes) {
        this.server = server;
        this.createdTime = LocalDateTime.now();
        this.cpuUsagePercent = cpuUsagePercent;
        this.usedMemoryBytes = usedMemoryBytes;
        this.usedDiskBytes = usedDiskBytes;
    }
}
