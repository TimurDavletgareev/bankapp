FROM amazoncorretto:21
COPY target/*.jar bankapp-exchange.jar
ENTRYPOINT ["java","-Dspring.profiles.active=default","-jar","/bankapp-exchange.jar"]
