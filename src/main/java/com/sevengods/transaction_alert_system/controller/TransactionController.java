package com.sevengods.transaction_alert_system.controller;

import com.sevengods.transaction_alert_system.dto.TransactionRequest;
import com.sevengods.transaction_alert_system.model.Transaction;
import com.sevengods.transaction_alert_system.service.FraudDetectionService;
import com.sevengods.transaction_alert_system.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final List<Transaction> transactionLog = new ArrayList<>();
    private final FraudDetectionService fraudDetectionService;
    private final NotificationService notificationService;

    public TransactionController(FraudDetectionService fraudDetectionService, NotificationService notificationService) {
        this.fraudDetectionService = fraudDetectionService;
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Transaction> processTransaction(@RequestBody TransactionRequest request) {
        boolean isFraud = fraudDetectionService.analyzeForFraud(request.getAmount());
        String status = isFraud ? "SUSPENDED_FOR_REVIEW" : "APPROVED";

        Transaction newTransaction = new Transaction(
                UUID.randomUUID(),
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency(),
                status,
                LocalDateTime.now(),
                isFraud
        );

        transactionLog.addFirst(newTransaction);

        notificationService.sendSmsAlert(newTransaction, "DEBIT");

        return ResponseEntity.ok(newTransaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionLog);
    }
}