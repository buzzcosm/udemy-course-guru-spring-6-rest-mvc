package com.buzzcosm.spring6restmvc.mappers;

import com.buzzcosm.spring6restmvc.entities.Customer;
import com.buzzcosm.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CustomerMapper {

    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
