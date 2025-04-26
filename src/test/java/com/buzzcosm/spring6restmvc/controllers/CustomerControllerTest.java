package com.buzzcosm.spring6restmvc.controllers;

import com.buzzcosm.spring6restmvc.model.Customer;
import com.buzzcosm.spring6restmvc.services.CustomerService;
import com.buzzcosm.spring6restmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    public static final String API_V_1_CUSTOMERS = "/api/v1/customers";
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @Test
    void testListCustomers() throws Exception {
        given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mockMvc.perform(get(API_V_1_CUSTOMERS).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCustomerById() throws Exception {
        Customer testCustomer = customerServiceImpl.getAllCustomers().getFirst();

        given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);

        mockMvc.perform(get(API_V_1_CUSTOMERS + "/" + testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is(testCustomer.getName())));
    }
}