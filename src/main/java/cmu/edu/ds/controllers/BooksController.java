package cmu.edu.ds.controllers;

import cmu.edu.ds.Models.Books;
import cmu.edu.ds.client.BooksClient;
import cmu.edu.ds.errors.CustomFeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controller for Books endpoints
@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksClient booksClient;

    public BooksController(BooksClient booksClient) {
        this.booksClient = booksClient;
    }

    @PostMapping
    public ResponseEntity<Object> addBook(@RequestBody Books book) {
        try {
            Object result = booksClient.addBook(book);
//            return ResponseEntity.ok(result);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (CustomFeignException e) {
            return ResponseEntity.status(e.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getResponseBody());
        }
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Object> getBookByIsbn(@PathVariable String isbn) {
        try {
            Object result = booksClient.getBookByIsbn(isbn);
            return ResponseEntity.ok(result);
        } catch (CustomFeignException e) {
            return ResponseEntity.status(e.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getResponseBody());
        }
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Object> getBookByIsbnAlt(@PathVariable String isbn) {
        try {
            Object result = booksClient.getBookByIsbnAlt(isbn);
            return ResponseEntity.ok(result);
        } catch (CustomFeignException e) {
            return ResponseEntity.status(e.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getResponseBody());
        }
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Object> updateBook(@PathVariable String isbn, @RequestBody Books book) {
        try {
            Object result = booksClient.updateBook(isbn, book);
            return ResponseEntity.ok(result);
        } catch (CustomFeignException e) {
            return ResponseEntity.status(e.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getResponseBody());
        }
    }
}