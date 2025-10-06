package com.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.models.Book;



public interface BookRepository extends MongoRepository<Book, String>{
	List<Book> findByNameContainingIgnoreCase(String name);
	
	// Tìm kiếm chỉ các book đang active
	List<Book> findByActiveTrue();
	
	// Tìm kiếm book theo ID và đang active
	Optional<Book> findByIdAndActiveTrue(String id);
	
	// Tìm kiếm theo tên trong các book đang active
	List<Book> findByNameContainingIgnoreCaseAndActiveTrue(String name);
	
	// Tìm kiếm tất cả book đã bị xóa mềm
	List<Book> findByActiveFalse();
}
