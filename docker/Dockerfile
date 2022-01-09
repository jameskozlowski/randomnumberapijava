FROM openjdk:17.0.1-jdk-slim AS build
WORKDIR /workspace/app

COPY . /workspace/app
RUN ./gradlew clean build
RUN mkdir -p build/dependency && cd build/dependency && jar -xf ../libs/randomnumberapi-0.0.1-SNAPSHOT.jar

FROM openjdk:17-jdk-alpine

COPY --from=build /workspace/app/build/libs/randomnumberapi-0.0.1-SNAPSHOT.jar /app/randomnumberapi-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT [ "java", "-jar", "/app/randomnumberapi-0.0.1-SNAPSHOT.jar", "--server.port=8081" ]
