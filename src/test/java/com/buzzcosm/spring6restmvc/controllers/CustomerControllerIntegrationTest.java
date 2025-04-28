package com.buzzcosm.spring6restmvc.controllers;

import com.buzzcosm.spring6restmvc.entities.Customer;
import com.buzzcosm.spring6restmvc.mappers.CustomerMapper;
import com.buzzcosm.spring6restmvc.model.CustomerDTO;
import com.buzzcosm.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIntegrationTest {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;

    @Test
    void testListCustomers() {
        List<CustomerDTO> customers = customerController.getAllCustomers();

        assertThat(customers.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> customers = customerController.getAllCustomers();

        assertThat(customers.size()).isEqualTo(0);
    }

    @Test
    void testGetCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());

        assertThat(customerDTO).isNotNull();
        assertThat(customerDTO.getId()).isEqualTo(customer.getId());
    }

    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer() {

        // build new customerDTO
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("TEST")
                .build();

        // call controller
        ResponseEntity responseEntity = customerController.saveNewCustomer(customerDTO);

        // check response
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        // pick new customer id from location
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        // check customer was created in repository
        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateExistingCustomer() {
        // Get first customer from repository
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        // Prepare payload
        customerDTO.setId(null);
        customerDTO.setVersion(null);

        // Update `name`
        final String customerName = "UPDATED";
        customerDTO.setName(customerName);

        // Call controller to get response from `updateCustomerById` method
        ResponseEntity responseEntity = customerController.updateCustomerById(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204)); // NO_CONTENT

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(customerName);
    }

    @Test
    void testUpdateCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> {
           customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteCustomerByIdFound() {
        // Get first customer from repository
        Customer customer = customerRepository.findAll().get(1);

        // Call controller to get response from `deleteCustomerById` method
        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204)); // NO_CONTENT

        assertThat(customerRepository.findById(customer.getId()).isEmpty()).isTrue();
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomerById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testPatchCustomerById() {
        // Get first customer from repository
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        // Prepare payload
        customerDTO.setId(null);
        customerDTO.setVersion(null);

        // Update `name`
        final String customerName = "UPDATED";
        customerDTO.setName(customerName);

        // Call controller to get response from `patchCustomerById` method
        ResponseEntity responseEntity = customerController.patchCustomerById(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204)); // NO_CONTENT

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(customerName);
    }
}