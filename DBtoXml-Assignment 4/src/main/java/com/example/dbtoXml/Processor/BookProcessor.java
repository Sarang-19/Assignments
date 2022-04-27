package com.example.dbtoXml.Processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.dbtoXml.model.Book;
@Component
public class BookProcessor implements ItemProcessor<Book,Book> {

	@Override
	public Book process(Book item) throws Exception {
		if(item.getBook_author().startsWith("J")) {
			return null;
		}
		return item;
	}
	

}
