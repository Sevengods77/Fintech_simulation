package com.sevengods.transaction_alert_system.controller;

import com.sevengods.transaction_alert_system.dto.TransactionRequest;
import com.fintech.transaction_alert_system.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@CrossOrigin(origins = "*") // Allows the frontend to communicate with this API
public class TransactionController {

    private final List<Transaction> transactionLog = new ArrayList<>();
    private static final BigDecimal FLAG_THRESHOLD = new BigDecimal("10000.00");

    @PostMapping
    public ResponseEntity<Transaction> processTransaction(@RequestBody TransactionRequest request) {
        String status = request.getAmount().compareTo(FLAG_THRESHOLD) >= 0 ? "SUSPENDED_FOR_REVIEW" : "APPROVED";

        Transaction newTransaction = new Transaction(
                UUID.randomUUID(),
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency(),
                status,
                LocalDateTime.now()
        );

        transactionLog.addFirst(newTransaction); // Add to beginning of list
        return ResponseEntity.ok(newTransaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionLog);
    }
}