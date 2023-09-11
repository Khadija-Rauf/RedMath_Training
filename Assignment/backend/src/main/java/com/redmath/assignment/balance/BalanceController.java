package com.redmath.assignment.balance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bank/accounts/balances")
@CrossOrigin("*")
public class BalanceController {
    private final BalanceService service;

    public BalanceController(BalanceService service) {
        this.service = service;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Balance>> findAll() {
        return ResponseEntity.ok(service.findAll());

    }
    @GetMapping("/{account_id}")
    public ResponseEntity<Object> findAllByAccountId(@PathVariable("account_id") Long account_id) {
        Optional<Balance> transactions = service.getBalance(account_id);

        if (transactions.isEmpty()) {
            String message = "No Balance found for account id: " + account_id;
            return ResponseEntity.ok(message);
        }

        return ResponseEntity.ok(transactions);
    }
    @GetMapping("update/{amount}")
    public ResponseEntity<Balance> updateBalance(@PathVariable("account_id") Long account_id, @PathVariable("amount") double amount, @PathVariable("indicator") String indicator) {
        try {
            Balance updatedBalance = service.updateBalance(amount, account_id,indicator);
            return ResponseEntity.ok(updatedBalance);
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<Balance> create(@RequestBody Balance balance) {
        Balance created = service.create(balance);
        if (created != null) {
            return ResponseEntity.ok(created);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}