package cmu.edu.ds.client;

import cmu.edu.ds.Models.Books;
import cmu.edu.ds.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "A1", url = "http://internal-bookstore-dev-InternalALB-126543766.us-east-1.elb.amazonaws.com:3000/books", configuration = FeignConfig.class)
//@FeignClient(name = "A1", url = "http://localhost:3000/books", configuration = FeignConfig.class)
public interface BooksClient {

    @PostMapping
    Object addBook(Books book);

    @GetMapping("/{isbn}")
    Object getBookByIsbn(@PathVariable("isbn") String isbn);

    @GetMapping("/isbn/{isbn}")
    Object getBookByIsbnAlt(@PathVariable("isbn") String isbn);

    @GetMapping("/status")
    Object getStatus();

    @PutMapping("/{isbn}")
    Object updateBook(@PathVariable("isbn") String isbn, @RequestBody Books book);

}
