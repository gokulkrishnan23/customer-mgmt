package com.tcs.customer_management.controllers;

import com.tcs.customer_management.dto.CustomerResponseDto;
import com.tcs.customer_management.services.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerResponseDto customer) {
        CustomerResponseDto createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable UUID id) {
        Optional<CustomerResponseDto> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<CustomerResponseDto> getCustomerByName(@RequestParam(required = false) String name,@RequestParam(required = false) String email) {
        CustomerResponseDto customers = null;
        if(StringUtils.isEmpty(name)){
            customers = customerService.getCustomersByEmail(email);
        }else if(StringUtils.isEmpty(email))
        {
            customers = customerService.getCustomersByName(name);
        }
        return ResponseEntity.ok(customers);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable UUID id, @RequestBody CustomerResponseDto customer) {
        Optional<CustomerResponseDto> updatedCustomer = customerService.updateCustomer(id, customer);
        return updatedCustomer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        boolean deleted = customerService.deleteCustomer(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
