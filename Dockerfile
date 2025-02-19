FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /src/advshop
COPY . .
# Berikan izin eksekusi pada gradlew
RUN chmod +x gradlew
RUN ./gradlew clean bootJar

FROM docker.io/library/eclipse-temurin:21-jre-alpine AS runner
ARG USER_NAME=app
ARG USER_UID=1000
ARG USER_GID=1000

RUN addgroup -g ${USER_GID} ${USER_NAME} \
    && adduser -h /opt/advshop -D -u ${USER_UID} -G ${USER_NAME} ${USER_NAME}

USER ${USER_NAME}
WORKDIR /opt/advshop
COPY --chown=${USER_NAME}:${USER_NAME} --from=builder /src/advshop/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]