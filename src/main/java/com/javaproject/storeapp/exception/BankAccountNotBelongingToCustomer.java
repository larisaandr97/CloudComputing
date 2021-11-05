package com.javaproject.storeapp.exception;

public class BankAccountNotBelongingToCustomer extends RuntimeException {
    public BankAccountNotBelongingToCustomer(int accountId, int customerId) {
        super("Bank account with Id " + accountId + " does not belong to customer with Id " + customerId + ".");
    }
}
