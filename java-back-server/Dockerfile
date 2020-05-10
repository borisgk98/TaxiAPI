FROM gradle:5.2.1-jdk11-slim as builder

COPY --chown=gradle:gradle . /home/gradle/builddir
WORKDIR /home/gradle/builddir
WORKDIR src/main/resources
#RUN rm application.properties
#RUN cp deploy.properties application.properties
RUN cp application.dist.yaml application.yaml
WORKDIR ../../..
RUN gradle bootJar

FROM openjdk:11-jre-slim
EXPOSE 8080
COPY --from=builder /home/gradle/builddir/build/libs/api-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app
CMD ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]