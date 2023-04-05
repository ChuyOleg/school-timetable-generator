# Stage 1
FROM maven:3.6.3-openjdk-17 as build

WORKDIR /opt/demo

COPY pom.xml .

RUN mvn dependency:go-offline

COPY ./src ./src

RUN mvn clean install

# Stage 2
FROM eclipse-temurin:17.0.6_10-jre-alpine

WORKDIR /opt/demo

COPY ./src ./src

COPY --from=build /opt/demo/target/school-generator-api.jar /opt/demo

EXPOSE 8080

ENTRYPOINT java -jar school-generator-api.jar
