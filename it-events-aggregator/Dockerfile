FROM maven:3.8.3-openjdk-17
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ADD ./pom.xml $PROJECT_DIR
RUN mvn dependency:resolve
ADD ./src $PROJECT_DIR/src
RUN mvn install

FROM bellsoft/liberica-openjdk-alpine:17.0.4-8
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/it-events-aggregator*.jar $PROJECT_DIR

EXPOSE 8080

CMD ["java", "-jar", "/opt/project/it-events-aggregator-0.0.1-SNAPSHOT.jar"]