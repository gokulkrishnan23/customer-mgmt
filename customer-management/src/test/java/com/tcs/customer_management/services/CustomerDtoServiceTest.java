package com.tcs.customer_management.services;

import com.tcs.customer_management.dto.CustomerResponseDto;
import com.tcs.customer_management.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerDtoServiceTest {

    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Test
    void getCustomerById_shouldReturnCustomerResponseDto_whenCustomerExists() {
        // Given
        UUID id = UUID.randomUUID();
        CustomerResponseDto customer = new CustomerResponseDto();
        customer.setId(id);
        customer.setName("Gokul krish");
        customer.setAnnualSpend(new BigDecimal("1500"));
        customer.setLastPurchaseDate(LocalDate.now().minusMonths(5));
      //  when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        // When
        Optional<CustomerResponseDto> result = customerService.getCustomerById(id);
        // Then
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("Gokul krish", result.get().getName());
        assertEquals("Gold", result.get().getTier()); // Adjust based on your logic!
    }
    @Test
    void getCustomerById_shouldReturnEmpty_whenCustomerNotFound() {
        UUID id = UUID.randomUUID();
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        Optional<CustomerResponseDto> result = customerService.getCustomerById(id);
        assertFalse(result.isPresent());
    }

    @Test
    void createCustomer_shouldSetIdAndSaveCustomer() {
        // Arrange
        CustomerResponseDto customer = new CustomerResponseDto();
        customer.setName("Alice");
        customer.setEmail("alice@example.com");
        // Mock save() to return the same customer
      //  when(customerRepository.save(any(CustomerResponseDto.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // Act
        CustomerResponseDto createdCustomer = customerService.createCustomer(customer);
        // Assert
        assertNotNull(createdCustomer.getId()); // UUID should be set
        assertEquals("Alice", createdCustomer.getName());
        assertEquals("alice@example.com", createdCustomer.getEmail());
        // Verify repository.save() was called with the right customer
     //   verify(customerRepository).save(createdCustomer);
    }
}
