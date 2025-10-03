package com.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.models.Book;



public interface BookRepository extends MongoRepository<Book, String>{
	List<Book> findByNameContainingIgnoreCase(String name);
}
