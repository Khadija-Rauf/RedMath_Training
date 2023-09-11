package com.redmath.assignment.balance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redmath.assignment.account.Account;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long balance_id;
    private LocalDate date;
    private double Amount;
    private String Indicator;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    public Long getId() {
        return balance_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }


    public void setId(Long id) {
        this.balance_id = id;
    }
    public void setAmount(double amount) {
        Amount = amount;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getIndicator() {
        return Indicator;
    }

    public void setIndicator(String Indicator) {
        this.Indicator = Indicator;
    }

}
