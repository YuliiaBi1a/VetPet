# 1. Usamos la imagen
FROM amazoncorretto:21.0.5-alpine

# 2. Copiamos el archivo JAR dentro del contenedor
COPY target/veterinaria-0.0.1-SNAPSHOT.jar app.jar

# 3. Ejecutamos la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
