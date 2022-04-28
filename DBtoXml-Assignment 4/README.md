This assignment is regarding reading data from a Database, processing it and adding it to the Xml file.

The configurations related to the batch are done in the BatchConfig class which is annotated with @Configuration and @EnableBatchProcessing.@Configuration annotation helps us to define beans for reader, writer and processor object. @EnableBatchProcessing annotation enables the spring batch features over that class.

The reader object is created with JdbcCursorItemReader, since the data needs to be read from an database and the database connection details comes from the application.properties file.
The reader object takes in an sql query, which fetch data from the database. 
Then the data needs to be mapped to the object of Book class with attributes bookname, authorname and bookId.
This mapping is done with the help of implementing RowMapper interface which extracts data from the result set and sets that data to the object attributes.

The BookProcessor class which implements ItemProcessor interface, validates whether the bookname is starting with "J". If the condition is true , then the object 
will be passed on the writer.

The writer used here is a object of StaxEventItemWriter class , since we need to write the data to a XML file. I created a empty file in the class path and added the path to writer object.
The writer object takes in XStreamMarshaller object which is used to convert the Book objects to XML format.The marshaller object takes in a map object, which is used to specify 
the roottag name and to which class object it is referring to as a Key,value pair.

Finally, the job has been configured with a single step and the step consisting of the above mentioned reader,processor and writer objects.

The output file(Books.xml) contains the processed data in XML format.
