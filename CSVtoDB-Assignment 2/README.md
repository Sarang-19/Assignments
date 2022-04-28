This assignment is regarding reading data from a CSV file , processing it and writing it to the Database.

The configurations related to the batch are done in the BatchConfig class which is annotated with @Configuration and @EnableBatchProcessing.@Configuration annotation helps us to define beans for reader, writer and processor object. @EnableBatchProcessing annotation enables the spring batch features over that class.

I created a reader object throught FlatFileItemReader class, which reads data from the CSV file available in the class path.
For mapping the fields to the attributes of Student class object, the reader object requires an DefaultLineMapper object which maps each line to a Student object and it 
tokenizes the fields based on a delimiter. The BeanWrapperFieldMapper object , then maps the fields to the specified class object.

There is also a StudentProcessor class which implements ItemProcessor interface, this class takes in Student object and return Student object after a validation. In 
this case, the processor has been used to validate whether the student objects have marks greater than 45. So, only the objects which satisfy this condition will be passed 
to the writer.

StudentWriter class implements ItemWriter interface, it is the place where the objects passed from the processor are persisted to the database.
For adding data to the database, I created a repository which implements JpaRepository.So, inside the StudentWriter class the objects have been saved to the database.

Finally, a job  has been configured with a single step and that step contains the above mentioned reader, processor and writer.

Database related details have been mentioned in the application.properties file.

Since, this is a spring boot application, the batch runs by default whenever the application is started. We don't need to use JobLauncher for that purpose.
