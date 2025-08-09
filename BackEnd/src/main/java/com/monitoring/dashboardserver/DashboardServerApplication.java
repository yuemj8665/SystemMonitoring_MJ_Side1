package com.monitoring.dashboardserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.monitoring.dashboardserver.domain.Server;
import com.monitoring.dashboardserver.repository.ServerRepository;

@SpringBootApplication
public class DashboardServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashboardServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ServerRepository serverRepository){
        return args -> {
            Server server1 = Server.builder()
        .serviceId("SERVICE01")
        .serviceName("SERVICE01 메인 API 서버 (임시)")
        .os("window 10")
        .totalCpuCores(0)
        .totalMemoryBytes(17179869184L)
        .totalDiskBytes(536870912000L)
        .build();
Server server2 = Server.builder()
        .serviceId("SERVICE02")
        .serviceName("SERVICE02 메인 API 서버 (임시)")
        .os("window 10")
        .totalCpuCores(0)
        .totalMemoryBytes(17179869184L)
        .totalDiskBytes(536870912000L)
        .build();
Server server3 = Server.builder()
        .serviceId("SERVICE Default")
        .serviceName("SERVICE Default 메인 API 서버 (임시)")
        .os("window 10")
        .totalCpuCores(0)
        .totalMemoryBytes(17179869184L)
        .totalDiskBytes(536870912000L)
        .build();

        serverRepository.save(server1);
        serverRepository.save(server2);
        serverRepository.save(server3);
        };
    }
}
