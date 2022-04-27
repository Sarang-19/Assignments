package com.restservice.bookhandler.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restservice.bookhandler.Implementation.BookImplementation;
import com.restservice.bookhandler.model.Book;

@RestController
public class BookController {
	@Autowired
	BookImplementation bookimplementation;
	@RequestMapping(path="/books",produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public List<Book> getAllBooks(){
		return bookimplementation.getAllBooks();
	}
	
	@PostMapping("/Addbook")
	public ResponseEntity<String> addBook(@RequestBody Book book){
		bookimplementation.addBook(book);
		return new ResponseEntity<String>("Added Successfully",HttpStatus.ACCEPTED);
	}
	

}
