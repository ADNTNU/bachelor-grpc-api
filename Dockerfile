FROM openjdk:21-slim

WORKDIR /usr/src/app

COPY target/BachelorGrpcApi.jar /usr/src/app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "BachelorGrpcApi.jar"]