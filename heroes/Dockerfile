FROM openjdk:11-jre-slim
VOLUME /tmp
ADD /target/spring-boot-heroes.jar spring-boot-heroes.jar
ENTRYPOINT ["java","-jar","/spring-boot-heroes.jar"]