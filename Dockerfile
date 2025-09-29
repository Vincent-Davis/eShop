FROM docker.io/library/eclipse-temurin:25-jdk-alpine@sha256:f4c0b771cfed29902e1dd2e5c183b9feca633c7686fb85e278a0486b03d27369 AS builder
WORKDIR /src/advshop
COPY . .
# Berikan izin eksekusi pada gradlew
RUN chmod +x gradlew
RUN ./gradlew clean bootJar

FROM docker.io/library/eclipse-temurin:25-jre-alpine@sha256:0be0f9b8c4c9c55bd97194ed84f0751f639f5712d9e99a45a0d65bf49f57bd08 AS runner
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