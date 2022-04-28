This assignment is regarding creating a spring batch application which reads data from a xml file, processes it and add it to the database.

The configurations related to the batch are done in the BatchConfig class which is annotated with @Configuration and @EnableBatchProcessing, so that we could define beans 
for reader, writer and processor object. @EnableBatchProcessing annotation enables the spring batch features over that class.

