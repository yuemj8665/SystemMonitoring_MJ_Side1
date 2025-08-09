# SystemMonitoring_MJ_Side1

시스템 모니터링 대시보드 프로젝트입니다.

## 🏗️ 프로젝트 구조

```
SystemMonitoring_MJ_Side1/
├── BackEnd/                          # Spring Boot 백엔드
│   ├── src/main/java/com/monitoring/dashboardserver/
│   │   ├── config/                   # 설정 클래스
│   │   ├── controller/               # 컨트롤러
│   │   ├── domain/                   # 엔티티
│   │   ├── dto/                      # 데이터 전송 객체
│   │   ├── repository/               # 리포지토리
│   │   └── service/                  # 서비스
│   └── src/main/resources/
│       └── application.properties    # 애플리케이션 설정
│
└── FrontEnd/                         # React + Vite 프론트엔드
    ├── src/
    │   ├── api/                      # API 클라이언트
    │   ├── components/               # React 컴포넌트
    │   └── data/                     # 목업 데이터
    └── package.json
```

## 🛠️ 기술 스택

### 백엔드
- **Framework**: Spring Boot
- **Build Tool**: Gradle
- **Language**: Java 17+

### 프론트엔드
- **Framework**: React 18
- **Build Tool**: Vite
- **UI Library**: Material-UI (MUI)
- **HTTP Client**: Axios
- **Language**: JavaScript (JSX)

## 🚀 실행 방법

### 백엔드 실행
```bash
cd BackEnd
./gradlew bootRun
```

### 프론트엔드 실행
```bash
cd FrontEnd
npm install
npm run dev
```

## 📡 API 엔드포인트

- `GET /api/v1/server/{serviceId}/info` - 개별 서버 정보 조회
- `GET /api/v1/server` - 모든 서버 정보 조회

## 🔧 주요 기능

- 서버 상태 모니터링 대시보드
- 실시간 시스템 정보 표시
- 반응형 UI (Material-UI 사용)
- CORS 설정 완료

## 📝 개발자

- MJ
