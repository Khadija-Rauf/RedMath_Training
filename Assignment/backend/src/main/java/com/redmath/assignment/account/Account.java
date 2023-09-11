package com.redmath.assignment.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redmath.assignment.balance.Balance;
import com.redmath.assignment.transaction.Transaction;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private String address;
    private String Role;

    @JsonIgnore
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Balance Balance;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> Transaction;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getRole() { return Role; }

    public void setRole(String role) { this.Role = role; }
    public  Balance getBalance() {
        return Balance;
    }

    public void setBalance(Balance balance) {
        Balance = balance;
    }

    public List<Transaction> getTransaction() {
        return Transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        Transaction = transaction;
    }
}