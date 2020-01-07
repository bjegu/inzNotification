FROM openjdk:11.0.5
VOLUME /tmp
COPY target/inz-notification-1.0-SNAPSHOT-spring-boot.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]