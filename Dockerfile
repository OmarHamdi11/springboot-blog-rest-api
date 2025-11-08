# always dockerfile starts with FROM instruction

FROM eclipse-temurin:21

LABEL mantainer="omarellafy1@gmail.com"

WORKDIR /app

COPY target/springboot-blog-rest-api-0.0.1-SNAPSHOT.jar /app/springboot-blog-rest-api.jar

ENTRYPOINT ["java", "-jar", "springboot-blog-rest-api.jar"]