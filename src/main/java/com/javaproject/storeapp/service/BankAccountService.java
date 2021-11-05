package com.javaproject.storeapp.service;

import com.javaproject.storeapp.entity.BankAccount;
import com.javaproject.storeapp.entity.User;

import java.util.List;

public interface BankAccountService {
    BankAccount createBankAccount(BankAccount bankAccount);

    List<BankAccount> getBankAccountsForUser(User user);

    BankAccount findBankAccountById(int id);

    void withdrawMoneyFromAccount(int accountId, double balance);

}
