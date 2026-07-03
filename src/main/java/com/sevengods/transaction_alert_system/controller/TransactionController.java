package com.sevengods.transaction_alert_system.controller;

import com.sevengods.transaction_alert_system.dto.TransactionRequest;
import com.sevengods.transaction_alert_system.model.Transaction;
import com.sevengods.transaction_alert_system.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@CrossOrigin(origins = "*") // Allows the frontend to communicate with this API
public class TransactionController {

    private final TransactionRepository repository;
    private static final BigDecimal FLAG_THRESHOLD = new BigDecimal("10000.00");

    public TransactionController(TransactionRepository repository) {
        this.repository = repository;
    }

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

        Transaction savedTransaction = repository.save(newTransaction);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(repository.findAll());
    }
}