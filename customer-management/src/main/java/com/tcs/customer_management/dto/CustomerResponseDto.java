package com.tcs.customer_management.dto;

import com.tcs.customer_management.constants.CustomerTier;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class CustomerResponseDto {
    private UUID id;
    private String name;

    public CustomerResponseDto(UUID id, String name, String email, BigDecimal annualSpend, LocalDate lastPurchaseDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.annualSpend = annualSpend;
        this.lastPurchaseDate = lastPurchaseDate;

    }

    private String email;
    private BigDecimal annualSpend;
    private LocalDate lastPurchaseDate;
    private CustomerTier tier;
}
