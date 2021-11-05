package com.javaproject.storeapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "accountNumber")
    private String accountNumber;
    private double balance;
    @Column(name = "cardNumber")
    private String cardNumber;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account")
    private List<Order> orders;

    public BankAccount() {
    }

    public BankAccount(String accountNumber, double balance, String cardNumber, User user) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.user = user;
    }

    public BankAccount(int id, String accountNumber, double balance, String cardNumber, User user) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.user = user;
    }
}
