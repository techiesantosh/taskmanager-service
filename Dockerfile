FROM openjdk:8-jdk-alpine


WORKDIR /app

COPY . /app


EXPOSE 8080
LABEL maintainer=“schougule2.t@gmail.com”
ADD ./target/taskmanager-0.0.1-SNAPSHOT.jar taskmanager-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","taskmanager-0.0.1-SNAPSHOT.jar"]