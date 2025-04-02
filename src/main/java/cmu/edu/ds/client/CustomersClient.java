package cmu.edu.ds.client;//package client;

import cmu.edu.ds.Models.Customer;
//import configuration.FeignConfig;
//import models.Customer;
import cmu.edu.ds.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

// Feign client for Customers Service (via ALB)
@FeignClient(name = "customers-service", url = "internal-bookstore-dev-InternalALB-126543766.us-east-1.elb.amazonaws.com:3000/customers", configuration = FeignConfig.class)
//@FeignClient(name = "customers-service", url = "http://localhost:3001/customers", configuration = FeignConfig.class)
public interface CustomersClient {

    @PostMapping
    Object addCustomer(Customer customer);

    @GetMapping("/{id}")
    Object getCustomerById(@PathVariable("id") String id);

    @GetMapping
    Object getCustomerByUserId(@RequestParam("userId") String userId);

    @GetMapping("/status")
    Object getStatus();
}
