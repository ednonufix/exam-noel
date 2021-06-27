FROM adoptopenjdk/openjdk11:jdk-11.0.7_10-alpine-slim

# Add System Info
LABEL system="musala-exam" \
    name="musala-api" \
    type="microservice"

# Optional values
ENV APP_NAME="demo"
ENV BUILD_DIR="build/libs"

# Set environments variables
ENV TZ=America/Havana

RUN apk add --update tzdata

COPY ./target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
