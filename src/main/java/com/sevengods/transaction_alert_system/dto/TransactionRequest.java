package com.sevengods.transaction_alert_system.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String accountId;
    private BigDecimal amount;
    private String currency;
}