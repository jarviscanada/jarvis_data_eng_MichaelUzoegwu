FROM amazoncorretto:23-alpine
COPY target/jdbc*.jar /usr/local/app/jdbc/jdbc.jar
WORKDIR /usr/local/app/jdbc
ENTRYPOINT ["java","-jar","jdbc.jar"]