FROM openjdk:16
EXPOSE 8083
ADD previous.csv previous.csv
ADD current.csv current.csv
ADD spring-batch-delta.jar spring-batch-delta.jar
ENTRYPOINT ["java","-jar","/spring-batch-delta.jar"]