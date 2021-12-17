FROM registry.access.redhat.com/ubi8/openjdk-11:1.10

# Add System Info
LABEL system="musala-exam" \
    name="musala-api" \
    type="microservice"

# Optional values
ENV APP_NAME="musala-exam"
ENV BUILD_DIR="build/libs"

# Set environments variables
ENV TZ=America/Havana

COPY ./target/*.jar /app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
