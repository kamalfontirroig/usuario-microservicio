FROM openjdk:17
EXPOSE 8080
ADD build/libs/api-usuarios-v1.0.jar api-usuarios-v1.0.jar
ENTRYPOINT ["java","-jar","/api-usuarios-v1.0.jar"]