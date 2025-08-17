package com.monitoring.dashboardserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.monitoring.dashboardserver.domain.MetricHistory;

public interface MetricHistoryRepository extends JpaRepository<MetricHistory, Long> {
    
}
