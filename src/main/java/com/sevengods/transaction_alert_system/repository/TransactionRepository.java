package com.sevengods.transaction_alert_system.repository;

import com.sevengods.transaction_alert_system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}