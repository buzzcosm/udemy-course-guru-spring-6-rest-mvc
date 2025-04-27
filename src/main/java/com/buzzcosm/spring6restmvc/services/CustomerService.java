package com.buzzcosm.spring6restmvc.services;

import com.buzzcosm.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(UUID id);

    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);

    void deleteCustomerById(UUID id);

    void patchCustomerById(UUID id, Customer customer);
}
