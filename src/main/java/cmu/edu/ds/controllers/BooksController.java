package cmu.edu.ds.controllers;//package controllers;

import cmu.edu.ds.Models.Books;
import cmu.edu.ds.client.BooksClient;
import cmu.edu.ds.errors.CustomFeignException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

// Controller for Books endpoints
@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksClient booksClient;

    public BooksController(BooksClient booksClient) {
        this.booksClient = booksClient;
    }

//        @GetMapping
//        public ResponseEntity<Object> getAllBooks() {
//            return ResponseEntity.ok(booksClient.getAllBooks());
//        }

    @PostMapping
    public ResponseEntity<Object> addBook(@RequestBody Books book) {
        try {
            Object result = booksClient.addBook(book);
            return ResponseEntity.ok(result);
        } catch (CustomFeignException e) {
            try {
                // Convert the response body to the appropriate format
//                String responseBody = StreamUtils.copyToString(e.getBody().asInputStream(), StandardCharsets.UTF_8);
                String responseBody = StreamUtils.copyToString(e.getResponseBody(), StandardCharsets.UTF_8);
                return ResponseEntity.status(e.getStatus()).body(responseBody);
            } catch (IOException ioException) {
                // Fallback if we can't read the response body
                return ResponseEntity.status(e.getStatus()).build();
            }
        }
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Object> getBookByIsbn(@PathVariable String isbn) {
        try {
            Object result = booksClient.getBookByIsbn(isbn);
            return ResponseEntity.ok(result);
        } catch (CustomFeignException e) {
            try {
                String responseBody = StreamUtils.copyToString(e.getResponseBody(), StandardCharsets.UTF_8);
                return ResponseEntity.status(e.getStatus()).body(responseBody);
            } catch (IOException ioException) {
                return ResponseEntity.status(e.getStatus()).build();
            }
        }
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Object> getBookByIsbnAlt(@PathVariable String isbn) {
        try {
            Object result = booksClient.getBookByIsbnAlt(isbn);
            return ResponseEntity.ok(result);
        } catch (CustomFeignException e) {
            try {
                String responseBody = StreamUtils.copyToString(e.getResponseBody(), StandardCharsets.UTF_8);
                return ResponseEntity.status(e.getStatus()).body(responseBody);
            } catch (IOException ioException) {
                return ResponseEntity.status(e.getStatus()).build();
            }
        }
    }
}


//@RestController
//@RequestMapping("/books")
//public class BooksController {
//    private final BooksClient booksClient;
//
//    public BooksController(BooksClient booksClient) {
//        this.booksClient = booksClient;
//    }
//
//    @GetMapping("/{isbn}")
//    public ResponseEntity<?> getBook(
//            @PathVariable String isbn,
//            @RequestHeader("X-Client-Type") String clientType) {
//
//        // Validate client type first
//        if (!"Web".equals(clientType)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of(
//                            "error", "Invalid client type",
//                            "message", "This endpoint is for web clients only"
//                    ));
//        }
//
//        try {
//            ResponseEntity<Book> response = booksClient.getBookByIsbn(isbn, clientType);
//            return ResponseEntity.ok(response.getBody());
//        } catch (FeignException e) {
//            return ResponseEntity.status(e.status())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(e.contentUTF8()); // Forward exact error from book-service
//        }
//    }
//}