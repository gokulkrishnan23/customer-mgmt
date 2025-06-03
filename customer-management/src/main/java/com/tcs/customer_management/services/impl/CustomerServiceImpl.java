package com.tcs.customer_management.services.impl;

import com.tcs.customer_management.constants.CustomerTier;
import com.tcs.customer_management.dto.CustomerResponseDto;
import com.tcs.customer_management.entities.Customer;
import com.tcs.customer_management.repositories.CustomerRepository;
import com.tcs.customer_management.services.CustomerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The Class CustomerServiceImpl - includes all business logic
 * required to perform operations on customer resource
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepo;

	public CustomerServiceImpl(CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
	}

	@Override
	public CustomerResponseDto createCustomer(CustomerResponseDto customerdto) {
		Customer customer = dtoToEntity(customerdto);
		return mapToDto(customerRepo.save(customer));
	}
	@Override
	public Optional<CustomerResponseDto> getCustomerById(UUID id) {
		Optional<Customer> customerOptional = customerRepo.findById(id);
		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			CustomerResponseDto responseDto = mapToDto(customer);
			responseDto.setTier(calculateTier(responseDto));
			return Optional.of(responseDto);
		} else {
			return Optional.empty();
		}
	}
	@Override
	public CustomerResponseDto getCustomersByName(String name) {
		CustomerResponseDto customerResponseDto = customerRepo.findByName(name);
		customerResponseDto.setTier(calculateTier(customerResponseDto));
		return customerResponseDto;
	}
	@Override
	public CustomerResponseDto getCustomersByEmail(String email) {
		CustomerResponseDto customerResponseDto = customerRepo.findByEmail(email);
		customerResponseDto.setTier(calculateTier(customerResponseDto));
		return customerResponseDto;
	}
	@Override
	public Optional<CustomerResponseDto> updateCustomer(UUID id, CustomerResponseDto updatedCustomer) {
		Optional<Customer> existingCustomerOptional = customerRepo.findById(id);
		if (existingCustomerOptional.isPresent()) {
			Customer existingCustomer = existingCustomerOptional.get();
			existingCustomer.setName(updatedCustomer.getName());
			existingCustomer.setEmail(updatedCustomer.getEmail());
			existingCustomer.setAnnualSpend(updatedCustomer.getAnnualSpend());
			existingCustomer.setLastPurchaseDate(updatedCustomer.getLastPurchaseDate());
			Customer savedCustomer = customerRepo.save(existingCustomer);
			return Optional.of(mapToDto(savedCustomer));
		} else {
			return Optional.empty();
		}
	}
	@Override
	public boolean deleteCustomer(UUID id) {
		if (customerRepo.existsById(id)) {
			customerRepo.deleteById(id);
			return true;
		}
		return false;
	}

	// Tier calculation logic
	private CustomerTier calculateTier(CustomerResponseDto customer) {
		if (customer.getAnnualSpend() == null) {
			return CustomerTier.SILVER; // default if no spend
		}
		BigDecimal annualSpend = customer.getAnnualSpend();
		LocalDate lastPurchaseDate = customer.getLastPurchaseDate();
		LocalDate now = LocalDate.now();
		if (annualSpend.compareTo(new BigDecimal("10000")) >= 0) {
			if (lastPurchaseDate != null && Period.between(lastPurchaseDate, now).getMonths() <= 6) {
				return CustomerTier.PLATINUM;
			}
		} else if (annualSpend.compareTo(new BigDecimal("1000")) >= 0) {
			if (lastPurchaseDate != null && Period.between(lastPurchaseDate, now).getYears() == 0) {
				return CustomerTier.GOLD;
			}
		}
		return CustomerTier.SILVER;
	}

	private CustomerResponseDto mapToDto(Customer customer) {
		CustomerResponseDto dto = new CustomerResponseDto();
		dto.setId(customer.getCustomerId());
		dto.setName(customer.getName());
		dto.setEmail(customer.getEmail());
		dto.setAnnualSpend(customer.getAnnualSpend());
		dto.setLastPurchaseDate(customer.getLastPurchaseDate());
		return dto;
	}
	private Customer dtoToEntity(CustomerResponseDto customerDto) {
		Customer customer = new Customer();
		customer.setName(customerDto.getName());
		customer.setAnnualSpend(customerDto.getAnnualSpend());
		customer.setEmail(customerDto.getEmail());
		customer.setLastPurchaseDate(customerDto.getLastPurchaseDate());
		return customer;
	}

}
