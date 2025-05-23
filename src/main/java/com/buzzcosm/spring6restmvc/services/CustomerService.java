package com.buzzcosm.spring6restmvc.services;

import com.buzzcosm.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer);

    Boolean deleteCustomerById(UUID id);

    Optional<CustomerDTO> patchCustomerById(UUID id, CustomerDTO customer);
}
