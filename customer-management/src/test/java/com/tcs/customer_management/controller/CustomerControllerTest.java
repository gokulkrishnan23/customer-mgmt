package com.tcs.customer_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.customer_management.controllers.CustomerController;
import com.tcs.customer_management.dto.CustomerResponseDto;
import com.tcs.customer_management.services.CustomerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void createCustomer_shouldReturnCreatedCustomer() throws Exception {
        CustomerResponseDto customer = new CustomerResponseDto();
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        CustomerResponseDto createdCustomer = new CustomerResponseDto();
        createdCustomer.setId(UUID.randomUUID());
        createdCustomer.setName(customer.getName());
        createdCustomer.setEmail(customer.getEmail());
        when(customerService.createCustomer(any(CustomerResponseDto.class))).thenReturn(createdCustomer);
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
    @Test
    void getCustomerById_shouldReturnCustomer() throws Exception {
        UUID id = UUID.randomUUID();
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(id);
        customerResponseDto.setName("Jane Smith");
        customerResponseDto.setEmail("jane@example.com");
        when(customerService.getCustomerById(id)).thenReturn(Optional.of(customerResponseDto));
        mockMvc.perform(get("/customers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.email").value("jane@example.com"));
    }
    @Test
    void getCustomerById_shouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(customerService.getCustomerById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/customers/{id}", id))
                .andExpect(status().isNotFound());
    }
    @Test
    void getCustomersByName_shouldReturnCustomers() throws Exception {
        String name = "Alice";
        CustomerResponseDto customer = new CustomerResponseDto();
        customer.setId(UUID.randomUUID());
        customer.setName(name);
        when(customerService.getCustomersByName(name)).thenReturn(customer);
        mockMvc.perform(get("/customers").param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name));
    }
    @Test
    void getCustomerByEmail_shouldReturnCustomer() throws Exception {
        String email = "bob@example.com";
        CustomerResponseDto customer = new CustomerResponseDto();
        customer.setId(UUID.randomUUID());
        customer.setName("Bob");
        customer.setEmail(email);
        when(customerService.getCustomersByEmail(email)).thenReturn(customer);
        mockMvc.perform(get("/customers").param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }
    @Test
    void updateCustomer_shouldReturnUpdatedCustomer() throws Exception {
        UUID id = UUID.randomUUID();
        CustomerResponseDto updateRequest = new CustomerResponseDto();
        updateRequest.setName("Updated Name");
        CustomerResponseDto updatedCustomer = new CustomerResponseDto();
        updatedCustomer.setId(id);

        updatedCustomer.setName(updateRequest.getName());
        updatedCustomer.setEmail("updated@example.com");
        when(customerService.updateCustomer(any(UUID.class), any(CustomerResponseDto.class)))
                .thenReturn(Optional.of(updatedCustomer));
        mockMvc.perform(put("/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }
    @Test
    void deleteCustomer_shouldReturnOk() throws Exception {
        UUID id = UUID.randomUUID();
        when(customerService.deleteCustomer(id)).thenReturn(true);
        mockMvc.perform(delete("/customers/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomer_shouldReturnNoContent_whenCustomerExists() throws Exception {
        UUID id = UUID.randomUUID();
        // Simulate that the service returns true (customer existed and deleted)
        when(customerService.deleteCustomer(id)).thenReturn(true);
        mockMvc.perform(delete("/customers/{id}", id))
                .andExpect(status().isNoContent());
    }
    @Test
    void deleteCustomer_shouldReturnNotFound_whenCustomerDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();
        // Simulate that the service returns false (customer not found)
        when(customerService.deleteCustomer(id)).thenReturn(false);
        mockMvc.perform(delete("/customers/{id}", id))
                .andExpect(status().isNotFound());
    }
}