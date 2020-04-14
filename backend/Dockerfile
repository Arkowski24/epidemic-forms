FROM gradle:jdk11

ADD . /covid-19-backend
WORKDIR /covid-19-backend

RUN ls -l
RUN gradle build
RUN chmod 777 /covid-19-backend/wait-for-it.sh

#---
FROM openjdk:11-jdk

VOLUME /tmp
COPY --from=0 "/covid-19-backend/build/libs/covid19tablet-*-SNAPSHOT.jar" app.jar
COPY --from=0 "/covid-19-backend/wait-for-it.sh" wait-for-it.sh

CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
