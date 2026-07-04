package com.sevengods.transaction_alert_system.service;

import com.sevengods.transaction_alert_system.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendSmsAlert(Transaction transaction, String transactionType) {
        String message;

        if ("CREDIT".equalsIgnoreCase(transactionType)) {
            message = String.format("Dear customer, your a/c %s has been credited with %s %s on %s. AVL Bal: XXXX.",
                    transaction.getAccountId(), transaction.getCurrency(), transaction.getAmount(), transaction.getTimestamp());
        } else if (transaction.isFraudulent()) {
            message = String.format("ALERT: Suspicious transaction of %s %s attempted on a/c %s. Status: %s.",
                    transaction.getCurrency(), transaction.getAmount(), transaction.getAccountId(), transaction.getStatus());
        } else {
            message = String.format("Txn Alert: %s %s debited from a/c %s on %s.",
                    transaction.getCurrency(), transaction.getAmount(), transaction.getAccountId(), transaction.getTimestamp());
        }

        System.out.println("=== SIMULATED SMS ===");
        System.out.println(message);
        System.out.println("=====================");
    }
}