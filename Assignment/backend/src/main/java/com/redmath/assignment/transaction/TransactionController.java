package com.redmath.assignment.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bank/accounts/transactions")
@CrossOrigin("*")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> findAll() {
        return ResponseEntity.ok(service.findAll());

    }
    @GetMapping("/{account_id}")
    public ResponseEntity<Object> findAllByAccountId(@PathVariable("account_id") Long account_id) {
        Optional<List<Transaction>>  transactions = service.getTransactionsByAccountId(account_id);

        if (transactions.isEmpty()) {
            String message = "No transactions found for account id: " + account_id;
            return ResponseEntity.ok(message);
        }

        return ResponseEntity.ok(transactions);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/{account_id}")
    public ResponseEntity<Transaction> create(@PathVariable("account_id") Long account_id, @RequestBody Transaction transaction) {
        Transaction created = service.create(account_id, transaction);
        if (created != null) {
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
