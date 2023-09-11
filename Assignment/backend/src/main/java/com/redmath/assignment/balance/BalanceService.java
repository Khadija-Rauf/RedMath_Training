package com.redmath.assignment.balance;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BalanceService {
    private final BalanceRepository balanceRepository;
    @Autowired
    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }
    public List<Balance> findAll(){
        return balanceRepository.findAll();
    }
    @Transactional
//    Get balance of an account
    public Optional<Balance> getBalance(Long account_id) {
        return balanceRepository.findByAccountId(account_id);
    }
//    update balance of an account w.r.t transactions
    @Transactional
    public Balance updateBalance(double amount, Long account_id, String indicator) {
        Optional<Balance> balanceOptional = balanceRepository.findByAccountId(account_id);
        if (balanceOptional.isPresent()) {
            Balance balance = balanceOptional.get();
            double currentBalance = balance.getAmount();
            double updatedBalance;
            if (Objects.equals(indicator, "1")) {
                updatedBalance = currentBalance + amount;
            } else if (Objects.equals(indicator, "0")) {
                if (currentBalance >= amount) {
                    updatedBalance = currentBalance - amount;
                } else {
                    throw new InsufficientBalanceException("Insufficient balance for account_id: " + account_id);
                }
            } else {
                throw new IllegalArgumentException("Invalid indicator value: " + indicator);
            }
            balance.setAmount(updatedBalance);
            balance.setDate(LocalDate.now());
            balanceRepository.save(balance);
            return balance;
        }
        return null;
    }
// deposit balance in an account
    public Balance create(Balance balance) {
        Long accountId = balance.getAccount().getId();
        Optional<Balance> existingBalance = balanceRepository.findByAccountId(accountId);
        if (existingBalance.isPresent()) {
            return null;
        } else {
            balance.setDate(LocalDate.now());
            Balance saved = balanceRepository.save(balance);
            return saved;
        }
}
}
