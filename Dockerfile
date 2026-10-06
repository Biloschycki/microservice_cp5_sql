FROM maven:3.9.8-eclipse-temurin-17-alpine AS build
WORKDIR /opt/app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine-3.23
WORKDIR /opt/app
COPY --from=build /opt/app/target/jogador.jar /opt/app/jogador.jar
ENV SPRING_PROFILES_ACTIVE=dev
EXPOSE 8080
CMD [ "java", "-jar", "jogador.jar" ]
