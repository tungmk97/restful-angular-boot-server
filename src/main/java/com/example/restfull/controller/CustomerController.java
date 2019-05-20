package com.example.restfull.controller;

import com.example.restfull.model.Customer;
import com.example.restfull.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        System.out.println("**********Get All customer********");

        return repository.findAll();
    }

    @PostMapping(value = "/customers/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        boolean isActive = true;
        customer = repository.save(new Customer(customer.getName(), customer.getAge(), isActive));

        return  customer;
    }

    @DeleteMapping(value = "/customers/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long id) {
        this.repository.deleteById(id);

        return new ResponseEntity<>("Delete ok", HttpStatus.OK);
    }

    @DeleteMapping(value = "/customers/deleteAll")
    public ResponseEntity<String> deleteAllCustomer() {
        this.repository.deleteAll();

        return new ResponseEntity<>("Delete All ok", HttpStatus.OK);
    }

    @GetMapping(value = "/customers/age/{age}")
    public List<Customer> findByAge(@PathVariable("age") int age) {
        return repository.findByAge(age);
    }

    @PutMapping(value = "/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        Optional<Customer> customerData = repository.findById(id);
        if(customerData.isPresent()) {
            Customer _customer = customerData.get();
            _customer.setName(customer.getName());
            _customer.setAge(customer.getAge());
            _customer.setActive(customer.isActive());
            return new ResponseEntity<>(repository.save(_customer), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
