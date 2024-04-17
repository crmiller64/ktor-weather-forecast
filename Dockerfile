# Build and package the application into a fat JAR
FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

# Copy the fat JAR to the container
FROM openjdk:11
# Define ports to expose dynamically with 'docker run -p ####:#### ...'
#EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/backend/build/libs/fat.jar /app/fat.jar

# Run the fat JAR in the container
ENTRYPOINT ["java","-jar","/app/fat.jar"]