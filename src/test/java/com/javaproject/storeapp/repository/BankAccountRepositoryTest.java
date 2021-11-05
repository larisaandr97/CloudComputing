package com.javaproject.storeapp.repository;

import com.javaproject.storeapp.entity.BankAccount;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
@Slf4j
public class BankAccountRepositoryTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    public void findBankAccountByCardNumberTest() {
        BankAccount bankAccount = bankAccountRepository.findBankAccountByCardNumber("4217737771886809");
        assertNotNull(bankAccount);
    }

    @Test
    public void findBankAccountsByUserTest() {
        List<BankAccount> accounts = bankAccountRepository.findBankAccountsByUser(1);
        assertEquals(2, accounts.size());
    }
}
