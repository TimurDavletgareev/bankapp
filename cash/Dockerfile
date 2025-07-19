FROM amazoncorretto:21
COPY target/*.jar bankapp-cash.jar
ENTRYPOINT ["java","-Dspring.profiles.active=default","-jar","/bankapp-cash.jar"]
