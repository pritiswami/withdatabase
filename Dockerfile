FROM openjdk:17
EXPOSE 4545
ADD target/spring-security-jwt.jar spring-security-jwt.jar
ENTRYPOINT ["java","-jar","/spring-security-jwt.jar"]
