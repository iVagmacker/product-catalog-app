FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app

COPY . /app

RUN mvn clean install

FROM amazoncorretto:17

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]