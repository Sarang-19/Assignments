This assignment is regarding dockerizing a spring batch application. In this case, I had used a spring batch application which reads data from the database and writes it to the csv file.
For dockerising this application, I had used Play with Docker platform https://labs.play-with-docker.com/ .

Step1: We need to create the dockerfile for the spring batch application with the following commands
        FROM openjdk:16
        ADD dbtocsv-0.0.1-SNAPSHOT.jar app.jar
        ENTRYPOINT ["java","-jar","app.jar"]
 Using FROM command , we are pulling a java image over which the application can run. The ADD command helps in adding the jar file to the container in the name app.jar. The ENTRYPOINT command is used to specify the command that need to be run while initializing the container.
 
 Step2: This application requires a database connection, we need to create a container with database image. This done with the help of docker-compose.yml file which helps in dealing with multiple containers.
 The docker-compose.yml file looks like
                       version: '3'
                      services:
                       my-app:
                          build: .
                          container_name: my-app
                          ports:
                            - "8080:8080"
                          depends_on:
                            - app-db
                          environment:
                            - SPRING_DATASOURCE_URL=jdbc:postgresql://app-db:5432/batch
                            - SPRING_DATASOURCE_USERNAME=postgres
                            - SPRING_DATASOURCE_PASSWORD=1234
                       app-db:
                          image: postgres
                          container_name: app-db
                          environment:
                            - POSTGRES_USER=postgres
                            - POSTGRES_PASSWORD=1234
                            - POSTGRES_DB=batch
                          ports:
                            - "5432:5432"
                            
  version - specifies the version of the compose file.
  services- specifies the applications such as my-app specifies spring batch application and app-db specifies postgresql database
  build- It helps in creating the image with the dockerfile specified in the root directory
  depends_on - It helps in linking both the containers, so first the database container is created and then the spring application container is created.
  environment- It is used to specify the database connection strings.
  POSTGRES_DB- It helps in creating a database while the container is initialised.
  
  Then, we need to add the jar file, dockerfile and docker-compose.yml file to the editor tab of the play with docker platform.
  
Step 3: use docker-compose up command, based on the docker-compose.yml file it creates the containers.

Step4: In this case, reading from the database we need to create a table and add values into the table.
      1. docker exec -it db_containername bash (It opends the database container in interactive mode )x
      2.psql -U user_name (mention the username for authentication)
      3.use \l to see the list of databases
      4. use\c database_name for entering the particular database
      5. we can use queries here for creating the table and adding the values into the table
      6. then we can exit the bash
      
Step5:we can use command docker-compose up, so that application works.

Step6: To fetch the output file from the container, we can enter the container by
    1.docker exec -it container_name bash
    2. ls (list of files)
    3.cat file_name (open the file)
    
  
