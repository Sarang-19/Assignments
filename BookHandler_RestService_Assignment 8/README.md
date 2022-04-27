This assignment is regarding creating a rest controller which can produce both XML and Json Response with a single request.

It has a model class Book, which has attributes like bookid,bookname, authorname and rating of the book.
It also has a JpaRepository which connects with a Postgresql database to store the book objects using Hibernate.
It has a rest controller which can either accept a POST request of adding a book object to the repository or GET request for fetching all the records from the database.
Whenever, the request reaches the controller based on the mapping , respected function is called. 
The function makes a call to the implementation class which has the dependency of the repository through which the implementation class connects with the databas

For adding book object to the database,it is mapped with the url localhost:XXXX/Addbook and for fetching books, it is mapped with the url localhost:XXXX/books 

By default, rest controller provides Json response but in order to get XML response, I had used "produces" attribute with request mapping through which we can select the mediaType
(produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}) 

while testing it through postman, by default we will be getting XML response , since it is given first in the produces attribute but if we want to change the response type,
inside header of postman, we can give key as "accept" and value as "application/xml" through which the mediaType will be changed to XML format, so that we can get a xml response.
