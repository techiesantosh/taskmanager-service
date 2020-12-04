## Multilayer docker and cache maven dependencies
## use Maven alpine
FROM maven:3.5.3-jdk-8-alpine  as build
WORKDIR /workspace/app

COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src

RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#Use jdk alpine
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.web.taskmanager.TaskManagerApplication"]
