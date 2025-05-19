# 使用官方Maven镜像作为构建环境
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

# 使用官方JRE镜像作为运行时环境
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/drawdb-server-1.0.0.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]