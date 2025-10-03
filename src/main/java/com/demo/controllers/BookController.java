package com.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private BookService userService;

    @GetMapping
    public List<Book> getAllBooks() {
        return userService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable(name="id") String id) {
        return userService.getBookById(id).orElse(null);
    }



    @GetMapping("/search")
    public List<Book> searchBook(@RequestParam(name="name") String name) {
        return userService.searchUsersByName(name);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return userService.createBook(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable(name="id") String id) {
        userService.deleteBook(id);
    }
}