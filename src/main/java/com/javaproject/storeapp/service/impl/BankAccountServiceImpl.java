package com.javaproject.storeapp.service.impl;

import com.javaproject.storeapp.entity.BankAccount;
import com.javaproject.storeapp.entity.User;
import com.javaproject.storeapp.exception.DuplicateCardNumberException;
import com.javaproject.storeapp.exception.ResourceNotFoundException;
import com.javaproject.storeapp.repository.BankAccountRepository;
import com.javaproject.storeapp.service.BankAccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        Optional<BankAccount> existingAccountCardNumber = Optional.ofNullable(bankAccountRepository.findBankAccountByCardNumber(bankAccount.getCardNumber()));
        existingAccountCardNumber.ifPresent(e -> {
            throw new DuplicateCardNumberException(bankAccount.getCardNumber());
        });
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public List<BankAccount> getBankAccountsForUser(User user) {
        return bankAccountRepository.findBankAccountsByUser(user.getId());
    }

    @Override
    public BankAccount findBankAccountById(int id) {
        Optional<BankAccount> accountOptional = Optional.ofNullable(bankAccountRepository.findBankAccountById(id));
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new ResourceNotFoundException("Bank account with Id " + id + " not found.");
        }
    }

    @Override
    public void withdrawMoneyFromAccount(int accountId, double balance) {
        BankAccount bankAccount = findBankAccountById(accountId);
        bankAccount.setBalance(balance);
        bankAccountRepository.save(bankAccount);
    }
}
