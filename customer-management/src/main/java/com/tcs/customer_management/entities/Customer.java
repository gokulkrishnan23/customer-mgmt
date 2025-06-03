package com.tcs.customer_management.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a Customer entity.
 */
@Entity
@Data
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue
	@Column(name = "customer_id")
	private UUID customerId;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	@Pattern(
			regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
			message = "Invalid email format"
	)
	@NotBlank(message = "Email is required")
	private String email;
	@Column(name = "annualSpend")
	private BigDecimal annualSpend;
	@Column(name = "lastPurchaseDate")
	private LocalDate lastPurchaseDate;
}
