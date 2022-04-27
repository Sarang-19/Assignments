package com.restservice.bookhandler.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restservice.bookhandler.model.Book;
@Repository
public interface BookRepository  extends JpaRepository<Book,Integer>{

}