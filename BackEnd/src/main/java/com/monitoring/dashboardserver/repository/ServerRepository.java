package com.monitoring.dashboardserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monitoring.dashboardserver.domain.Server;

// JapRepository<Entiry, Entiry의 id타입(String)>를 상속을 받으면 기본적인 JPA CRUD가 만들어진다.
public interface ServerRepository extends JpaRepository<Server, String> {

    // ServiceId가 고유 번호이기 때문에 그에 맞는 메소드 정의
    // Spring Data JPA가 메소드 이름을 보고 자동으로 만들어줌
    Optional<Server> findByServiceId(String serviceId);
    
}
