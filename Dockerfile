# Use the Official OpenJDK 17 image from Docker Hub
FROM eclipse-temurin:17-jre
# Set working directory inside the container
WORKDIR /app
# Copy the compiled Java application JAR file into the container (ECS)
COPY target/library-management-system-0.0.1-SNAPSHOT.jar app.jar
# Expose the port the Spring Boot application will run on
EXPOSE 8080
# Command to run the application
CMD ["java", "-jar", "library-management-system.jar"]


#                   by all above instructions the docker image is created
#                   and this docker image is created by AWS CodeBuild and push image to ECR
#                   but how does AWS Code Build knows this instruction ?
#                   it is by instructions written in Buildspec.yaml file to AWS CodeBuild to perform the task/operatons step 1 and step 2
#                   which is creating docker image and push that docker image to ECR
#                   and CodeDeploy is used to pull the appilcation to run on ECS from ECR
#














































