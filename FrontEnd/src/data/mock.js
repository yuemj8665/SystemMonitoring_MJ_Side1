// src/data/mock.js
// 기존 시스템 정보 조회 (/api/v1/servers/{serviceId}/info)
export const mockSystemInfoResponse = {
    timestamp: "2025-08-06T02:10:10Z",
    code: "200",
    message: "SUCCESS",
    data: {
        serviceId: "SERVICE01",
        serviceName: "My-Dev-Server",
        os: "Ubuntu 22.04.3 LTS",
        totalCpuCores: 8,
        totalMemoryBytes: 17179869184, // 16GB
        totalDiskBytes: 536870912000 // 500GB
    }
};

// 실시간 시스템 정보 조회 (/api/v1/servers/{serviceId}/metrics/realtime)
export const mockRealtimeMetricsResponse = {
        timestamp: "2025-08-06T02:10:10Z",
    code: "200",
    message: "SUCCESS",
    data: {
        serviceId: "SERVICE01",
        cpuUeagePercent: 22.8,
        usedMemoryBytes: 5368709120,
        usedDiskBytes: 118111600640
    }
}

export const mockServers = [
    {
    info: {
      serviceId: "SERVICE01",
      serviceName: "메인 API 서버",
      os: "Ubuntu 22.04.3 LTS",
      totalCpuCores: 8,
      totalMemoryBytes: 17179869184,
      totalDiskBytes: 536870912000,
    },
    metrics: {
      cpuUsagePercent: 22.8,
      usedMemoryBytes: 5368709120,
      usedDiskBytes: 118111600640,
    },
  },
  {
    info: {
      serviceId: "SERVICE02",
      serviceName: "배치 처리 서버",
      os: "Red Hat Enterprise Linux 9",
      totalCpuCores: 4,
      totalMemoryBytes: 8589934592,
      totalDiskBytes: 268435456000,
    },
    metrics: {
      cpuUsagePercent: 58.1,
      usedMemoryBytes: 6442450944,
      usedDiskBytes: 21474836480,
    },
  },
  {
    info: {
      serviceId: "SERVICE03",
      serviceName: "데이터베이스 서버",
      os: "Windows Server 2022",
      totalCpuCores: 16,
      totalMemoryBytes: 34359738368,
      totalDiskBytes: 1099511627776,
    },
    metrics: {
      cpuUsagePercent: 10.5,
      usedMemoryBytes: 12914013184,
      usedDiskBytes: 439804651110,
    },
  }
];