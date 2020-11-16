FROM adoptopenjdk/openjdk11-openj9:alpine-slim

ARG APP_NAME
ARG APP_VERSION
ARG TARGET_DIR=/opt/application
ARG TARGET_CONFIG_DIR=$TARGET_DIR/config

ARG SOURCE_DIR=build
ARG SOURCE_CONFIG_DIR=$SOURCE_DIR/resources/main
ARG SOURCE=$SOURCE_DIR/libs/$APP_NAME-$APP_VERSION.jar

EXPOSE 8080/tcp

RUN mkdir -p "$TARGET_DIR"
RUN mkdir -p "$TARGET_CONFIG_DIR"

ADD $SOURCE $TARGET_DIR/app.jar
ADD $SOURCE_CONFIG_DIR $TARGET_CONFIG_DIR

CMD ["/bin/sh","-c","java -jar /opt/application/app.jar", "-XX:+UseContainerSupport"]