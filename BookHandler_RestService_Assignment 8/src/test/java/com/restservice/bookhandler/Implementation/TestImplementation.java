package com.restservice.bookhandler.Implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.restservice.bookhandler.Repository.BookRepository;
import com.restservice.bookhandler.model.Book;

@SpringBootTest
public class TestImplementation {
	@Autowired
	BookImplementation implementation;
	@Autowired
	BookRepository bookRepository;
	@Test
	public void testAddBook() {
		
		Book book=new Book(1,"Avatar","Spielberg",5);
		implementation.addBook(book);
		assertEquals(book.getBook_author(),
				bookRepository.findById(1).get().getBook_author());
	}
	
	@Test
	public void testGetAllBooks() {
		List<Book> listOfBooks=implementation.getAllBooks();
		assertEquals(1,listOfBooks.size());
	}
	
	@Test
	public void testUpdateBookRating() {
		Book book=implementation.updateBookRating(1, 2);
		int actual_rating=bookRepository.findById(1).get().getRating();
		assertEquals(book.getRating(),actual_rating);
	}
    
}
