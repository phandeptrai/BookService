package com.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.models.Book;
import com.demo.services.BookService;



@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(name="id") String id) {
        return bookService.getBookById(id)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Book> searchBook(@RequestParam(name="name") String name) {
        return bookService.searchUsersByName(name);
    }

    @GetMapping("/deleted")
    public List<Book> getDeletedBooks() {
        return bookService.getDeletedBooks();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(name="id") String id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Book đã được xóa mềm thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xóa book: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/restore")
    public ResponseEntity<String> restoreBook(@PathVariable(name="id") String id) {
        try {
            bookService.restoreBook(id);
            return ResponseEntity.ok("Book đã được phục hồi thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi phục hồi book: " + e.getMessage());
        }
    }
}