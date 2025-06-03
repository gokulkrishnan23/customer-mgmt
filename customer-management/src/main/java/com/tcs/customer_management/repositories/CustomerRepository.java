package com.tcs.customer_management.repositories;


import com.tcs.customer_management.dto.CustomerResponseDto;
import com.tcs.customer_management.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The Interface CustomerRepository.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("SELECT new com.tcs.customer_management.dto.CustomerResponseDto(c.id, c.name, c.email,c.annualSpend,c.lastPurchaseDate) FROM Customer c WHERE c.name = :name")
    CustomerResponseDto findByName(@Param("name") String name);

    @Query("SELECT new com.tcs.customer_management.dto.CustomerResponseDto(c.id, c.name, c.email,c.annualSpend,c.lastPurchaseDate) FROM Customer c WHERE c.email = :email")
    CustomerResponseDto findByEmail(@Param("email") String email);
}
