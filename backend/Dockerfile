FROM gradle:jdk11

ADD . /covid-19-backend
WORKDIR /covid-19-backend

RUN ls -l
RUN gradle build

#---
FROM openjdk:11-jdk

VOLUME /tmp
COPY --from=0 "/covid-19-backend/build/libs/covid19tablet-*-SNAPSHOT.jar" app.jar

CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
