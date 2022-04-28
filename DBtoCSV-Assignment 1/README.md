This project is regarding creating a Spring Batch application which reads data from Database, processes it and writes it to a CSV file.

The configurations related to the batch are done in the BatchConfig class which is annotated with @Configuration and @EnableBatchProcessing.@Configuration annotation helps us to define beans for reader, writer and processor object. @EnableBatchProcessing annotation enables the spring batch features over that class.

I created a reader object using JdbcCursorItemReader class which reads data from the database using a sql query and maps it to the Employee model class object through
EmployeeMapper class which implements RowMapper interface and maps each row of the database to a employee Object.

I also created a EmployeeProcessor class which implements ItemProcessor interface,it is for validating that all the employee objects that where written to the CSV file 
has a salary greater than 25000.

There is also writer object which was created by FlatFileItemWriter class, since we are going to write the data to a CSV file. This writer object fetches objects that has been passed by the  processor and writes it to the CSV file. But before writing into the CSV file, the fields needs to be extracted and mapped to a single line in csv file, so that writer object takes in  a DefaultLineMapper object, Which further takes the DelimitedLineAggregator object.

All the fields are grouped as a single line by the DelimitedLineAggregator class , through which we can also specify the delimiter to be used in the file for seperating the fields.

As a final step, the job need to be configured. In this assignment, the job is configured to be completed in  a single step and each step consists of a reader, processor and a writer.
