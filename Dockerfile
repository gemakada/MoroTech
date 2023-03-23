FROM maven:3.8.7-openjdk-18-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
RUN mvn verify clean --fail-never
COPY src /workspace/src
RUN mvn -f pom.xml clean package -Dspring.config.use-legacy-processing=true -Dspring.profiles.active=prod

FROM openjdk:18.0.2.1-slim
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8443
ENTRYPOINT ["java","-jar","app.jar"]

