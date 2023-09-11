package com.redmath.assignment.transaction;

import com.redmath.assignment.account.AccountService;
import com.redmath.assignment.balance.BalanceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BalanceService balanceService;
    @Autowired
    private AccountService accountService;
    public TransactionService(TransactionRepository transactionRepository, BalanceService balanceService){
        this.transactionRepository = transactionRepository;
        this.balanceService = balanceService;
    }
    public List<Transaction> findAll(){
        return transactionRepository.findAll();
    }
    @Transactional
    public Optional<List<Transaction>>  getTransactionsByAccountId(Long account_id) {
        return transactionRepository.findByAccountId(account_id);
    }
    @Transactional
    public Transaction create(Long account_id, Transaction transaction) {
        if (transaction.getId() != null && transactionRepository.existsById(transaction.getId())) {
            return null;
        }
        transaction.setDate(LocalDate.now());
        if (transaction.getDescription() == null)   transaction.setDescription("Description: Not available");
        transaction.setAccount(accountService.findById(account_id));
        Transaction saved = transactionRepository.save(transaction);
        balanceService.updateBalance(saved.getAmount(), saved.getAccount().getId(), saved.getIndicator());
        return saved;
    }
}
