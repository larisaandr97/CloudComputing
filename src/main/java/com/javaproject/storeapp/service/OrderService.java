package com.javaproject.storeapp.service;

import com.javaproject.storeapp.dto.OrderItemRequest;
import com.javaproject.storeapp.entity.BankAccount;
import com.javaproject.storeapp.entity.Order;
import com.javaproject.storeapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order findOrderById(int id);

    List<Order> getOrdersByUser(User user);

    Optional<Order> createOrder(User user, List<OrderItemRequest> orderItemRequests, int accountId);

    boolean checkBalanceForOrder(BankAccount bankAccount, double total);

    BankAccount validateBankAccount(int userId, int accountId);
}
