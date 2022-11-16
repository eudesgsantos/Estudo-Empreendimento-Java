## syntax=docker/dockerfile:1
#
#FROM openjdk:19
#
#COPY . /opt
#WORKDIR /opt
#
#ENTRYPOINT ["java","-jar","./target/empreendimento-0.0.1-SNAPSHOT.jar"]
#
## syntax=docker/dockerfile:1

FROM eclipse-temurin:19-jdk-jammy

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]