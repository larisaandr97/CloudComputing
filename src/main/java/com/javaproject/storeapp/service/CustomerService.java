package com.javaproject.storeapp.service;

import com.javaproject.storeapp.entities.BankAccount;
import com.javaproject.storeapp.entities.Customer;
import com.javaproject.storeapp.exception.CustomerNotFoundException;
import com.javaproject.storeapp.repository.BankAccountRepository;
import com.javaproject.storeapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;

    public CustomerService(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public Customer addCustomer(Customer c) {
        return customerRepository.save(c);
    }

    public Customer findCustomerById(int id) {
        Optional<Customer> customerOptional = Optional.ofNullable(customerRepository.findCustomerById(id));
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        } else {
            throw new CustomerNotFoundException(id);
        }
    }

    public BankAccount createBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public List<BankAccount> getBankAccountsForCustomer(int customerId) {
        return bankAccountRepository.findBankAccountsByCustomer(customerId);
    }
}
