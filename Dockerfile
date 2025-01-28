FROM openjdk:17-jdk
EXPOSE 8080
ADD ./spring-security-jwt.jar spring-security-jwt.jar
ENTRYPOINT ["java","-jar","/spring-security-jwt.jar"]
