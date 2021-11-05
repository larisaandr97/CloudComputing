package com.javaproject.storeapp.service;

import com.javaproject.storeapp.dto.OrderItemRequest;
import com.javaproject.storeapp.entity.*;
import com.javaproject.storeapp.exception.BankAccountNotBelongingToCustomer;
import com.javaproject.storeapp.exception.CartIsEmptyException;
import com.javaproject.storeapp.exception.InsufficientFundsException;
import com.javaproject.storeapp.exception.ResourceNotFoundException;
import com.javaproject.storeapp.repository.OrderItemRepository;
import com.javaproject.storeapp.repository.OrderRepository;
import com.javaproject.storeapp.service.impl.BankAccountServiceImpl;
import com.javaproject.storeapp.service.impl.CartServiceImpl;
import com.javaproject.storeapp.service.impl.OrderServiceImpl;
import com.javaproject.storeapp.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CartServiceImpl cartService;
    @Mock
    private ProductServiceImpl productService;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private BankAccountServiceImpl bankAccountService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("Find Order By Id - happy flow")
    public void findOrderByIdTestHappyFlow() {

        Order order = new Order();
        order.setId(1);
        when(orderRepository.findOrderById(order.getId()))
                .thenReturn(order);

        Order result = orderService.findOrderById(order.getId());

        assertNotNull(order.getId());
        assertEquals(order.getId(), result.getId());

    }

    @Test
    @DisplayName("Find Order By Id - order not found")
    public void findOrderByIdTestNotFound() {

        Order order = new Order();
        order.setId(1);
        when(orderRepository.findOrderById(order.getId()))
                .thenReturn(null);

        RuntimeException exception = assertThrows(ResourceNotFoundException.class, () -> orderService.findOrderById(order.getId()));
        assertEquals("Order with Id " + order.getId() + " not found.", exception.getMessage());
    }


    @Test
    @DisplayName("Create order - happy flow")
    public void createOrderTestHappyFlow() {
        //arrange
        User user = new User();
        user.setId(1);
        BankAccount bankAccount = new BankAccount(1, "3331965465", 200, "4331256148952346", user);
        Cart cart = new Cart(1, 100, user);
        Product product = new Product("Lego", "disney", 100.0, ProductCategory.TOYS, 20);
        product.setId(1);
        Order orderCreated = new Order(200, LocalDate.now(), user);
        orderCreated.setId(1);
        orderCreated.setAccount(bankAccount);
        List<OrderItemRequest> items = Collections.singletonList(new OrderItemRequest(product, 1, 100.0));
        OrderItem item = new OrderItem(1, 100, product);
        orderCreated.setOrderItems(Collections.singletonList(item));
        // when(customerService.findCustomerById(anyInt())).thenReturn(customer);
        when(cartService.findCartByUser(user)).thenReturn(cart);
        when(productService.findProductById(anyInt())).thenReturn(product);
        when(productService.updateStock(product.getId(), product.getStock() - item.getQuantity())).thenReturn(product);
        when(orderRepository.save(any())).thenReturn(orderCreated);
        when(orderItemRepository.save(any())).thenReturn(item);
        when(bankAccountService.findBankAccountById(anyInt())).thenReturn(bankAccount);

        //act
        Optional<Order> result = orderService.createOrder(user, items, bankAccount.getId());

        //assert
        // assertEquals(Arrays.asList(item), result.getOrderItems());
        assertEquals(cart.getTotalAmount(), result.get().getTotalAmount());
        assertEquals(user, result.get().getUser());

    }

    @Test
    @DisplayName("Get Orders by user - happy flow")
    public void getOrdersByUserHappyFlow() {
        User user = new User();
        user.setId(1);
        //   when(customerService.findCustomerById(customer.getId())).thenReturn(customer);
        when(orderRepository.findOrdersByUser(user)).thenReturn(Collections.singletonList(new Order(100, LocalDate.now(), user)));

        List<Order> result = orderService.getOrdersByUser(user);

        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findOrdersByUser(user);
        //  verify(customerService, times(1)).findCustomerById(customer.getId());
    }

    @Test
    @DisplayName("Create order - account not belonging to customer")
    public void createOrderTestAccountNotBelongingToCustomer() {
        User user = new User();
        user.setId(1);
        User user2 = new User();
        user2.setId(2);
        BankAccount bankAccount = new BankAccount(1, "3331965465", 200, "4331256148952346", user2);

        when(bankAccountService.findBankAccountById(anyInt())).thenReturn(bankAccount);
        BankAccountNotBelongingToCustomer exception = assertThrows(BankAccountNotBelongingToCustomer.class, () -> orderService.validateBankAccount(user.getId(), bankAccount.getId()));
        assertEquals("Bank account with Id " + bankAccount.getId() + " does not belong to customer with Id " + user.getId() + ".", exception.getMessage());
    }

    @Test
    @DisplayName("Create order - cart not found")
    public void createOrderTestCartNotFound() {
        User user = new User();
        user.setId(1);
        BankAccount bankAccount = new BankAccount(1, "3331965465", 200, "4331256148952346", user);

        when(bankAccountService.findBankAccountById(bankAccount.getId())).thenReturn(bankAccount);
        when(cartService.findCartByUser(any())).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(user, new ArrayList<>(), bankAccount.getId()));
        assertEquals("Cart for customer with Id " + user.getId() + " not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Create order - cart is empty")
    public void createOrderTestCartIsEmpty() {
        User user = new User();
        user.setId(1);
        BankAccount bankAccount = new BankAccount(1, "3331965465", 200, "4331256148952346", user);
        Cart cart = new Cart();
        cart.setTotalAmount(0);

        when(bankAccountService.findBankAccountById(bankAccount.getId())).thenReturn(bankAccount);
        when(cartService.findCartByUser(any())).thenReturn(cart);

        CartIsEmptyException exception = assertThrows(CartIsEmptyException.class, () -> orderService.createOrder(user, new ArrayList<>(), bankAccount.getId()));
        assertEquals("Cart for customer with Id " + user.getId() + " is empty! You must add some items before making an order.", exception.getMessage());
    }

    @Test
    @DisplayName("Check balance of bank account for order - happy flow")
    public void checkBalanceForOrderTestHappyFlow() {
        BankAccount bankAccount = new BankAccount("3331965465", 200, "4331256148952346", null);
        double totalAmount = 100;
        boolean result = orderService.checkBalanceForOrder(bankAccount, totalAmount);
        assert (result);
    }

    @Test
    @DisplayName("Check balance of bank account for order - insufficient funds")
    public void checkBalanceForOrderTestInsufficientFunds() {
        BankAccount bankAccount = new BankAccount(1, "3331965465", 200, "4331256148952346", null);
        double totalAmount = 350;

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> orderService.checkBalanceForOrder(bankAccount, totalAmount));
        assertEquals("Insufficient funds in Bank Account with Id " + bankAccount.getId() + ".", exception.getMessage());

    }
}
