package com.javaproject.storeapp.repository;

import com.javaproject.storeapp.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    BankAccount findBankAccountById(int id);

    BankAccount findBankAccountByCardNumber(String cardNumber);

    default List<BankAccount> findBankAccountsByUser(int id) {
        return this.findAll()
                .stream().filter(account -> account.getUser().getId() == id)
                .collect(Collectors.toList());
    }
}
