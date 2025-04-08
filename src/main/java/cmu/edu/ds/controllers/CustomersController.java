package cmu.edu.ds.controllers;

import cmu.edu.ds.Models.Customer;
import cmu.edu.ds.client.CustomersClient;
import cmu.edu.ds.errors.CustomFeignException;
import jakarta.validation.constraints.Email;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomersController {
    private final CustomersClient customersClient;

    public CustomersController(CustomersClient customersClient) {
        this.customersClient = customersClient;
    }

    @PostMapping
    public ResponseEntity<Object> addCustomer(@RequestBody Customer customer) {
        try {
            Object result = customersClient.addCustomer(customer);
            return ResponseEntity.ok(result);
        } catch (CustomFeignException e) {
            return ResponseEntity.status(e.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getResponseBody());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable String id) {
        try {
            Object result = customersClient.getCustomerById(id);
            return ResponseEntity.ok(result);
        } catch (CustomFeignException e) {
            return ResponseEntity.status(e.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getResponseBody());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getCustomerByUserId(@RequestParam("userId") @Email String userId) {
        try {
            Object result = customersClient.getCustomerByUserId(userId);
            return ResponseEntity.ok(result);
        } catch (CustomFeignException e) {
            return ResponseEntity.status(e.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getResponseBody());
        }
    }
}
