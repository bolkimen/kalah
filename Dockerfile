# syntax=docker/dockerfile:experimental

# Build image
FROM openjdk:15-slim as build

ENV MAVEN_OPTS='-Xmx1024M -Xss512k -XX:+PrintFlagsFinal -Xlog:gc -verbose:gc -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=256M -XX:CompressedClassSpaceSize=32M'

# create a group/user
RUN groupadd --gid 1000 buildgroup

# create a non-root user
RUN useradd --home-dir /home/builduser -s /bin/bash \
        --non-unique --uid 1000 --gid 1000 --groups sudo \
        builduser

RUN mkdir -p /home/builduser/.m2 \
    && mkdir -p /home/builduser/build_app
WORKDIR /home/builduser/build_app

RUN mkdir -p /usr/share/man/man1
RUN apt-get update -y && apt-get upgrade -y && apt-get -y install maven

COPY pom.xml .
RUN --mount=type=cache,mode=1777,uid=1000,target=/home/builduser/.m2/repository \
        mvn org.apache.maven.plugins:maven-dependency-plugin:go-offline -nsu -U -V --batch-mode -T4

COPY . .
RUN --mount=type=cache,mode=1777,uid=1000,target=/home/builduser/.m2/repository \
        mvn clean verify -U -V --batch-mode -Dexternal.maven.fixed-ports.skip=false -Dsurefire.useFile=false -T4 -Dmaven.test.failure.ignore=true

# Application image
FROM openjdk:15-slim as app

COPY --from=build /home/builduser/build_app/target/*.jar /srv/app/app.jar

ENTRYPOINT ["java", "-jar", "/srv/app/app.jar"]
