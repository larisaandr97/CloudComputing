package com.javaproject.storeapp.service;

import com.javaproject.storeapp.entity.BankAccount;
import com.javaproject.storeapp.entity.User;
import com.javaproject.storeapp.repository.BankAccountRepository;
import com.javaproject.storeapp.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase
//@Profile("h2")
//@TestPropertySource(locations = "classpath:application-test.properties")
public class BankAccountServiceIT {

    @Autowired
    private BankAccountServiceImpl bankAccountService;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @AfterEach
    void tearDown() {
        bankAccountRepository.deleteAll();
    }

    @Test
    @DisplayName("Create Bank Account - happy flow")
    public void createBankAccountHappyFlow() {

        User user = new User();
        BankAccount bankAccount = new BankAccount("3331965465", 200, "4331256148952346", user);

        BankAccount createdAccount = bankAccountService.createBankAccount(bankAccount);

        assertNotNull(createdAccount.getId());
        assertEquals(bankAccount.getAccountNumber(), createdAccount.getAccountNumber());
        assertEquals(bankAccount.getCardNumber(), createdAccount.getCardNumber());
        assertEquals(bankAccount.getBalance(), createdAccount.getBalance());
        assertEquals(bankAccount.getUser(), createdAccount.getUser());

    }
}
