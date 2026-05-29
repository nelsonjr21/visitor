# Étape 1 : Compilation du projet avec Maven et Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : Exécution de l'application avec un JRE Java 21 Temurin léger
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# On récupère le fichier .jar généré lors de l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Port par défaut de Spring Boot
EXPOSE 8080

# Commande de démarrage de ton API
ENTRYPOINT ["java", "-jar", "app.jar"]