package com.javaproject.storeapp.service;

import com.javaproject.storeapp.entity.BankAccount;
import com.javaproject.storeapp.entity.User;
import com.javaproject.storeapp.exception.DuplicateCardNumberException;
import com.javaproject.storeapp.exception.ResourceNotFoundException;
import com.javaproject.storeapp.repository.BankAccountRepository;
import com.javaproject.storeapp.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Test
    @DisplayName("Add a new BankAccount - happy flow")
    public void createBankAccountTestHappyFlow() {

        //arrange
        User user = new User();
        user.setId(1);
        BankAccount bankAccount = new BankAccount("3331965465", 200, "4331256148952346", user);
        BankAccount savedBankAccount = new BankAccount(1, "3331965465", 200, "4331256148952346", user);
        when(bankAccountRepository.save(bankAccount)).thenReturn(savedBankAccount);

        //act
        BankAccount result = bankAccountService.createBankAccount(bankAccount);

        //assert
        assertEquals(bankAccount.getCardNumber(), result.getCardNumber());
        assertEquals(bankAccount.getAccountNumber(), result.getAccountNumber());
        assertEquals(bankAccount.getBalance(), result.getBalance());
        verify(bankAccountRepository, Mockito.times((1))).save(bankAccount);
    }

    @Test
    @DisplayName("Add a new BankAccount - bank account with same card number already exists")
    public void createBankAccountTestDuplicateException() {

        //arrange
        User user = new User();
        user.setId(1);
        BankAccount bankAccount = new BankAccount("3331965465", 200, "4331256148952346", user);
        BankAccount savedBankAccount = new BankAccount(1, "3331965465", 200, "4331256148952346", user);
        when(bankAccountRepository.findBankAccountByCardNumber(any())).thenReturn(savedBankAccount);

        //act
        DuplicateCardNumberException exception = assertThrows(DuplicateCardNumberException.class, () -> bankAccountService.createBankAccount(bankAccount));

        //assert
        assertEquals("A bank account with the card number " + bankAccount.getCardNumber() + " already exists.", exception.getMessage());
        verify(bankAccountRepository, times(1)).findBankAccountByCardNumber(any());
        verify(bankAccountRepository, times(0)).save(any());

    }

    @Test
    @DisplayName("Get all BankAccounts for a Customer")
    public void getBankAccountForCustomerHappyFlow() {
        //arrange
        User user = new User();
        user.setId(1);

        BankAccount bankAccount1 = new BankAccount(1, "3331965465", 200, "4331256148952346", user);
        BankAccount bankAccount2 = new BankAccount(2, "4441965465", 340, "4229256148952346", user);

        user.setBankAccounts(Arrays.asList(bankAccount1, bankAccount2));

        when(bankAccountRepository.findBankAccountsByUser(user.getId()))
                .thenReturn(Arrays.asList(bankAccount1, bankAccount2));

        //act
        List<BankAccount> result = bankAccountService.getBankAccountsForUser(user);

        //assert
        assertEquals(user.getBankAccounts(), result);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getBalance(), 200);

        //verify(customerService, times(1)).findCustomerById(customer.getId());
        verify(bankAccountRepository, times(1)).findBankAccountsByUser(user.getId());
    }

    @Test
    @DisplayName("Find Bank Account By Id - happy flow")
    public void findBankAccountByIdTestHappyFlow() {

        BankAccount account = new BankAccount();
        account.setId(1);
        when(bankAccountRepository.findBankAccountById(account.getId()))
                .thenReturn(account);

        BankAccount result = bankAccountService.findBankAccountById(account.getId());

        assertNotNull(result.getId());
        assertEquals(account.getId(), result.getId());

    }

    @Test
    @DisplayName("Find Bank Account By Id - bank account not found")
    public void findBankAccountByIdTestNotFound() {

        BankAccount account = new BankAccount();
        account.setId(1);
        when(bankAccountRepository.findBankAccountById(account.getId()))
                .thenReturn(null);

        RuntimeException exception = assertThrows(ResourceNotFoundException.class, () -> bankAccountService.findBankAccountById(account.getId()));
        assertEquals("Bank account with Id " + account.getId() + " not found.", exception.getMessage());

    }

}
