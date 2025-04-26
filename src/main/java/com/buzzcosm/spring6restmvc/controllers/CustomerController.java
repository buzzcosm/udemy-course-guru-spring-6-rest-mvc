package com.buzzcosm.spring6restmvc.controllers;

import com.buzzcosm.spring6restmvc.model.Customer;
import com.buzzcosm.spring6restmvc.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        log.debug("Get all customers - in controller");
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public ResponseEntity saveNewCustomer(@RequestBody Customer customer) {

        Customer savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + savedCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity updateCustomerById(
            @PathVariable("customerId") UUID customerId,
            @RequestBody Customer customer
    ) {
        customerService.updateCustomerById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity patchCustomerById(@PathVariable("customerId") UUID id,
                                            @RequestBody Customer customer) {

        customerService.patchCustomerById(id, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID id) {

        customerService.deleteCustomerById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
