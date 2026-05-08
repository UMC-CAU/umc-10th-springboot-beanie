# umc_ch05_mission - 설정 안내

프로젝트 주요 설정과 실행 방법

1) 데이터베이스 환경 변수 설정 (Windows PowerShell 예시)

```powershell
# PowerShell에서 세션에만 설정 (터미널을 닫으면 사라집니다)
$env:DB_USER = "your_db_user"
$env:DB_PW = "your_db_password"
$env:DB_URL = "jdbc:mysql://localhost:3306/your_database?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true"

# 예시: gradle로 실행
.\gradlew.bat bootRun
```

2) application.yml

`src/main/resources/application.yml`에서 datasource 설정은 환경 변수 `DB_URL`, `DB_USER`, `DB_PW`를 사용합니다.

3) 응답 통일 및 예외 처리

- 공통 응답: `com.example.umc_ch05_mission.global.apiPayload.ApiResponse`
- 공통 에러 코드: `com.example.umc_ch05_mission.global.apiPayload.code.GeneralErrorCode`
- 전역 예외 처리기: `com.example.umc_ch05_mission.global.apiPayload.exception.GlobalExceptionHandler`

4) 빌드 및 실행

```powershell
Push-Location .\
.\gradlew.bat build --no-daemon
.\gradlew.bat bootRun
Pop-Location
```

문제가 발생하면 빌드 로그를 확인하고, DB 연결이 실패하면 환경 변수 값과 DB 접근 정보(JDBC URL, 포트, 사용자/비밀번호)를 확인하세요.
