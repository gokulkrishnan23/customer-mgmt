package com.tcs.customer_management.services;

import com.tcs.customer_management.dto.CustomerResponseDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The Interface CustomerService.
 */
@Service
public interface CustomerService {

     CustomerResponseDto createCustomer(CustomerResponseDto customer);

     Optional<CustomerResponseDto> getCustomerById(UUID id);

     CustomerResponseDto getCustomersByName(String name);

     CustomerResponseDto getCustomersByEmail(String email) ;

     Optional<CustomerResponseDto> updateCustomer(UUID id, CustomerResponseDto updatedCustomer);

     boolean deleteCustomer(UUID id) ;


}