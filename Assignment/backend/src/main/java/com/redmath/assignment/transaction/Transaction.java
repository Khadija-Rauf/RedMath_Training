package com.redmath.assignment.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redmath.assignment.account.Account;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;
    private LocalDate date;
    private String Description;
    private double Amount;
    private String Indicator;
    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;
    public Long getId() {
        return transaction_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
    public double getAmount() {
        return Amount;
    }
    public void setId(Long id) {
        this.transaction_id = id;
    }
    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getIndicator() {
        return Indicator;
    }

    public void setIndicator(String Indicator) {
        this.Indicator = Indicator;
    }

}
