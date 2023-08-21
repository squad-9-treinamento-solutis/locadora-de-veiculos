FROM openjdk
MAINTAINER github/silvaalejesus
COPY . /app/
WORKDIR /app
ENTRYPOINT ["java", "-jar", "example-jsongenerator-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080