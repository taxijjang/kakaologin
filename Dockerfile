# 1단계: Gradle로 빌드 (EC2에 Java 불필요)
FROM gradle:8.2.1-jdk17 AS builder

WORKDIR /app
COPY . .

# 테스트 생략하고 빌드
RUN gradle clean build -x test --no-daemon

# 2단계: 실제 실행용 이미지 (슬림)
FROM openjdk:17-ea-17-jdk-slim

WORKDIR /app

# 빌드된 jar만 복사
COPY --from=builder /app/build/libs/kakaologin-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 15232
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
