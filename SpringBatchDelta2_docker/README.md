This assignment is regarding dockerizing a spring batch application, in this case, I had dockerized assignment 7. It is reading data from previous and current csv files and creating a delta file with unique records. The records are written into success.csv file or reject.csv file based on the classifier condition.

For dockerising this application, I had used Play with Docker platform https://labs.play-with-docker.com/ .

I created a docker file with the following commands 

FROM openjdk:16
EXPOSE 8083
ADD previous.csv previous.csv
ADD current.csv current.csv
ADD spring-batch-delta.jar spring-batch-delta.jar
ENTRYPOINT ["java","-jar","/spring-batch-delta.jar"]

FROM command is used to specify the base image over which the application can run.
EXPOSE command is used to specify the port of the container that will be exposed .
ADD command is used to files, jar to the container.
ENTRYPOINT command is used to mention the commands that needs to be run, when the container is initialised.

To run this application in that platform, first, we need to add the dockerfile, jar file,previous.csv and current.csv files to the editor tab which is available in the platform.

previous.csv and current.csv files are the input files.

for building the image,
docker build -t "image_name" .
The above command helps in creating a image with the name specified after -t and . specifies adding all the files to the container.

for running the container,
docker run --name container_name image_name

NOTE: since, we are running it as a jar file, we cannot use ClassPathResource object for specifying the resources in the code, rather we can use FileSystemResource.

If we want to fetch the output files from the container, we can use

docker cp container_id:/file_name  file_name1
cp command is used to copy file from container to host or host to container.
here, we copying file_name to file_name1 in host.

If you want to see through the container, we can open the container in bash mode

1.docker exec -it container_name bash
2.ls (shows the list of files)
3.cat file_name(to open the file)

