package com.sevengods.transaction_alert_system.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class FraudDetectionService {

    private static final BigDecimal HIGH_VALUE_THRESHOLD = new BigDecimal("50000.00");

    public boolean analyzeForFraud(BigDecimal amount) {
        if (amount == null) {
            return false;
        }
        // Returns true if the amount is greater than or equal to 50,000.00
        return amount.compareTo(HIGH_VALUE_THRESHOLD) >= 0;
    }
}