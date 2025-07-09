# =================
# 1단계: 빌드(Build) 환경
# =================
# Maven과 Java 17(JDK)이 포함된 이미지를 기반으로 'builder'라는 별칭을 부여합니다.
FROM maven:3.9-eclipse-temurin-17 AS builder

# 작업 디렉토리를 생성합니다.
WORKDIR /app

# pom.xml 파일을 먼저 복사하여 의존성 레이어를 분리합니다.
# pom.xml이 변경되지 않으면 이 단계의 캐시를 재사용하여 빌드 속도가 향상됩니다.
COPY pom.xml .

# pom.xml을 기반으로 모든 프로젝트 의존성을 다운로드합니다.
RUN mvn dependency:go-offline

# 프로젝트의 나머지 소스 코드를 복사합니다.
COPY src ./src

# Maven을 사용하여 애플리케이션을 패키징(jar 파일 생성)합니다.
# 테스트는 CI 단계에서 이미 수행했거나 별도로 수행하므로 빌드 시에는 건너뛰어 속도를 높입니다.
RUN mvn package -DskipTests


# =================
# 2단계: 실행(Runtime) 환경
# =================
# 실제 애플리케이션을 실행할 환경입니다.
# JDK가 아닌 JRE(Java Runtime Environment)만 포함된 훨씬 가벼운 이미지를 사용합니다.
# Alpine 리눅스는 경량화로 유명합니다.
FROM eclipse-temurin:17-jre-alpine

# 작업 디렉토리를 생성합니다.
WORKDIR /app

# 1단계(builder)에서 생성된 jar 파일만 최종 이미지로 복사합니다.
# Maven, 소스코드 등 불필요한 파일들은 포함되지 않아 이미지 크기가 매우 작아집니다.
COPY --from=builder /app/target/*.jar app.jar

# 애플리케이션이 8080 포트를 사용함을 명시적으로 알립니다. (문서화 목적)
EXPOSE 8080

# 컨테이너가 시작될 때 이 명령어를 실행하여 Spring Boot 애플리케이션을 구동합니다.
ENTRYPOINT ["java", "-jar", "app.jar"]