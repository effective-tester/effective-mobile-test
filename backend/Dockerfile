FROM gradle:8.11.0-jdk21 AS build
WORKDIR /backend
COPY . .
RUN gradle bootJar

FROM openjdk:21-jdk-slim
WORKDIR /backend
COPY --from=build /backend/src/main/resources /backend/resources
COPY --from=build /backend/build/libs/*.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]
