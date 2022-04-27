package com.restservice.bookhandler.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restservice.bookhandler.Repository.BookRepository;
import com.restservice.bookhandler.model.Book;

@Service
public class BookImplementation {
	
	@Autowired
	BookRepository bookrepository;
  public List<Book> getAllBooks(){
	  List<Book> bookList=bookrepository.findAll();
	  return bookList;
  }
	public void addBook(Book book) {
		bookrepository.save(book);
	}
	
	public Book updateBookRating(int id,int rating) {
		Book book=bookrepository.findById(id).get();
		book.setRating(rating);
		bookrepository.save(book);
		return book;
	}
}
