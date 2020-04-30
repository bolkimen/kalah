FROM openjdk:15-slim

RUN mkdir -p /usr/share/man/man1

RUN apt-get update -y && apt-get upgrade -y && apt-get -y install maven

RUN mvn -version
RUN java --version