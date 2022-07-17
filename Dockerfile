FROM openjdk:8-jdk-alpine

COPY center.jar /app/

WORKDIR /app

EXPOSE 8080

CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "center.jar"]
