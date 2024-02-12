#
# Build stage
#
FROM eclipse-temurin:17-jdk-jammy AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN chmod +x mvnw && \
    ./mvnw -f $HOME/pom.xml clean package -DskipTests=true

#
# Package stage
#
FROM eclipse-temurin:17-jre-jammy
EXPOSE 8080
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
ENTRYPOINT java -jar /app/runner.jar