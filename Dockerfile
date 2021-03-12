FROM openjdk:11.0.7
EXPOSE 8080
VOLUME /tmp
RUN mkdir -p /app/logs/
COPY target/VocabularyBuilder-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/app.jar"]