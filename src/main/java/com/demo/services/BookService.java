package com.demo.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.models.Book;
import com.demo.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
    public List<Book> getAllBooks() {
        return bookRepository.findByActiveTrue();
    }

    public Optional<Book> getBookById(String id) {
        return bookRepository.findByIdAndActiveTrue(id);
    }

    public Book createBook(Book book) {
        // Đảm bảo book mới luôn active
        book.setActive(true);
        return bookRepository.save(book);
    }

    public void deleteBook(String id) {
    	// Soft delete - đánh dấu book là không active thay vì xóa hoàn toàn
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setActive(false);
            bookRepository.save(book);
        }
    }
    
    public void restoreBook(String id) {
        // Phục hồi book đã bị xóa mềm
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setActive(true);
            bookRepository.save(book);
        }
    }

    public List<Book> searchUsersByName(String name) {
        return bookRepository.findByNameContainingIgnoreCaseAndActiveTrue(name);
    }
    
    public List<Book> getDeletedBooks() {
        return bookRepository.findByActiveFalse();
    }
}
