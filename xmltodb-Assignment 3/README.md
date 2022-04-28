This assignment is regarding creating a spring batch application which reads data from a xml file, processes it and add it to the database.

The configurations related to the batch are done in the BatchConfig class which is annotated with @Configuration and @EnableBatchProcessing, so that we could define beans for reader, writer and processor object. @EnableBatchProcessing annotation enables the spring batch features over that class.

The reader object is created with StaxEventItemReader class which reads data from the product.xml file which is placed inside the resources folder of the classpath.
With the reader object, we need to add the rootfragmentelement name of the XML file.
The reader object needs a marshaller which converts the xml data to objects of the Product class, for this purpose I used Jaxb2Marshaller object which takes in the class Name that needs to be bounded.

The ProductProcessor class implements ItemProcessor interface, which validates the Product objects based on the price such that if the product object price is 25000, it will be passed to the writer .

The writer object is created with JdbcBatchItemWriter class, the writer object takes in datasource as the path for the destination database. This datasource object is autowired with the data coming from the application.properties file.This writer object will taking a insertion query to add the data to the database, that query has place holder(?), the values for those place holders will come from ItemPreparedStatementSetter interface implementation, which set value for each place holder starting from 1.

Finally, using JobBuilderFactory, we will be configuring the job with a single step, that step will be configured with a StepBuilderFactory with the above mentioned reader,processor and writer objects.

Since, this is a spring boot application, whenever the application runs,the job will get launched. It doesn't need a seperate job launcher.

