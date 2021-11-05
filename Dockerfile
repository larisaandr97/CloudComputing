FROM openjdk:16-alpine3.13 
VOLUME /tmp 
EXPOSE 10222 
ADD storeapp-0.0.1-SNAPSHOT.jar  storeapp-0.0.1-SNAPSHOT.jar 
ENTRYPOINT ["java","-jar","storeapp-0.0.1-SNAPSHOT.jar"]
