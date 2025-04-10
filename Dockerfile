FROM eclipse-temurin:21 AS build

WORKDIR /app

COPY pom.xml .
COPY src src

COPY mvnw .
COPY .mvn .mvn

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21

VOLUME /tmp
COPY --from=build /app/target/*.jar game-reviews.jar

ARG PORT
ENV PORT=${PORT}
EXPOSE ${PORT}

ARG POSTGRES_USER
ENV POSTGRES_USER=${POSTGRES_USER}

ARG POSTGRES_PASSWORD
ENV POSTGRES_PASSWORD=${POSTGRES_PASSWORD}

ARG JWT_SECRET
ENV JWT_SECRET=${JWT_SECRET}

ARG POSTGRES_HOST
ENV POSTGRES_HOST=${POSTGRES_HOST}

ENTRYPOINT ["java", "-jar", "/game-reviews.jar"]
