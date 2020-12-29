package com.javaproject.storeapp.service;

import com.javaproject.storeapp.dto.OrderItemRequest;
import com.javaproject.storeapp.entities.*;
import com.javaproject.storeapp.exception.BankAccountNotBelongingToCustomer;
import com.javaproject.storeapp.exception.BankAccountNotFoundException;
import com.javaproject.storeapp.exception.InsufficientFundsException;
import com.javaproject.storeapp.repository.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BankAccountRepository bankAccountRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, BankAccountRepository bankAccountRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    public Order findOrderById(int id) {
        return orderRepository.findOrderById(id);
    }

    public List<Order> getOrdersByCustomer(Customer customer) {
        return orderRepository.findOrdersByCustomer(customer);
    }

    @Transactional
    public Order createOrder(Customer customer, List<OrderItemRequest> orderItemRequests, BankAccount bankAccount) {
        // Creating order object
        Order order = new Order();
        order.setCustomer(customer);
        order.setAccount(bankAccount);

        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest item : orderItemRequests) {
            Product product = productRepository.findProductById(item.getProductId());
            int stock = product.getStock();
            // if there is available stock for the product, we add to list of order items
            if (stock >= item.getQuantity()) {
                product.setStock(stock - item.getQuantity());
                productRepository.save(product);
                total += product.getPrice() * item.getQuantity();
                orderItems.add(new OrderItem(item.getQuantity(), item.getPrice(), product));
            }
        }
        // check if there is enough money in the account to pay the order
        checkBalanceForOrder(bankAccount, total);
        order.setTotalAmount(total);
        order.setDatePlaced(LocalDate.now());

        // Saving entities in database
        orderRepository.save(order);
        orderItems.forEach(item -> {
            item.setOrders(order);
            orderItemRepository.save(item);
        });

        // withdraw money from bank account
        bankAccount.setBalance(bankAccount.getBalance() - total);
        bankAccountRepository.save(bankAccount);

        return order;
    }

    private void checkBalanceForOrder(BankAccount bankAccount, double total) {
        if (bankAccount.getBalance() < total)
            throw new InsufficientFundsException(bankAccount.getId());
    }

    public BankAccount validateBankAccount(int customerId, int accountId) {
        BankAccount bankAccount = bankAccountRepository.findBankAccountById(accountId);
        if (bankAccount == null)
            throw new BankAccountNotFoundException(accountId);
        if (customerId != bankAccount.getCustomer().getId())
            throw new BankAccountNotBelongingToCustomer(accountId, customerId);
        return bankAccount;
    }
}