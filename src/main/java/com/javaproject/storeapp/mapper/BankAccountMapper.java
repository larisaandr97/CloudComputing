package com.javaproject.storeapp.mapper;

import com.javaproject.storeapp.dto.BankAccountRequest;
import com.javaproject.storeapp.entity.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    public BankAccount bankAccountRequestToBankAccount(BankAccountRequest bankAccountRequest) {
        return new BankAccount(bankAccountRequest.getAccountNumber(),
                bankAccountRequest.getBalance(),
                bankAccountRequest.getCardNumber(), bankAccountRequest.getUser());
    }
}
