package com.javaproject.storeapp.service;

import com.javaproject.storeapp.dto.OrderItemRequest;
import com.javaproject.storeapp.entity.Cart;
import com.javaproject.storeapp.entity.Product;
import com.javaproject.storeapp.entity.User;

import java.util.List;

public interface CartService {
    Cart findCartByUser(User user);

    Cart createCart(User user, double value, OrderItemRequest item);

    void resetCart(Cart cart);

    OrderItemRequest getItemByProductId(int productId, int userId);

    int updateItemQuantity(int userId, OrderItemRequest item, int quantity);

    List<OrderItemRequest> deleteItemFromCart(Cart cart, int userId, int productId);

    List<OrderItemRequest> getCartContents(int userId);

    Product validateProduct(int productId, int quantity);

    Cart addProductToCart(User user, OrderItemRequest item);

    void updateCartAmount(int cartId, double value);

}
