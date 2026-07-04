package com.fintech.transaction_alert_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private UUID transactionId;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private LocalDateTime timestamp;
}