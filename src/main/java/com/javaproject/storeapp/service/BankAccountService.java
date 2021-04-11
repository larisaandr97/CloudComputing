package com.javaproject.storeapp.service;

import com.javaproject.storeapp.entity.BankAccount;
import com.javaproject.storeapp.entity.User;
import com.javaproject.storeapp.exception.BankAccountNotFoundException;
import com.javaproject.storeapp.exception.DuplicateCardNumberException;
import com.javaproject.storeapp.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount createBankAccount(BankAccount bankAccount) {
        Optional<BankAccount> existingAccountCardNumber = Optional.ofNullable(bankAccountRepository.findBankAccountByCardNumber(bankAccount.getCardNumber()));
        existingAccountCardNumber.ifPresent(e -> {
            throw new DuplicateCardNumberException(bankAccount.getCardNumber());
        });
        return bankAccountRepository.save(bankAccount);
    }

    public List<BankAccount> getBankAccountsForUser(User user) {
        //   Customer customer = customerService.findCustomerById(customerId);
        return bankAccountRepository.findBankAccountsByUser(user);
    }

    public BankAccount findBankAccountById(int id) {
        Optional<BankAccount> accountOptional = Optional.ofNullable(bankAccountRepository.findBankAccountById(id));
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new BankAccountNotFoundException(id);
        }
    }

    public void withdrawMoneyFromAccount(int accountId, double balance) {
        BankAccount bankAccount = findBankAccountById(accountId);
        bankAccount.setBalance(balance);
        bankAccountRepository.save(bankAccount);
    }
}
