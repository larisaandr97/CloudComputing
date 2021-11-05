package com.javaproject.storeapp.service.impl;

import com.javaproject.storeapp.dto.OrderItemRequest;
import com.javaproject.storeapp.entity.Cart;
import com.javaproject.storeapp.entity.Product;
import com.javaproject.storeapp.entity.User;
import com.javaproject.storeapp.exception.NegativeQuantityException;
import com.javaproject.storeapp.exception.ProductNotInCartException;
import com.javaproject.storeapp.exception.ProductNotInStockException;
import com.javaproject.storeapp.repository.CartRepository;
import com.javaproject.storeapp.service.CartService;
import com.javaproject.storeapp.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private Map<Integer, List<OrderItemRequest>> cartItems = new HashMap<>();
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    /**
     * Find cart by user method
     *
     * @param user {@link User}
     * @return {@link Cart} Object
     */
    @Override
    public Cart findCartByUser(User user) {
        return cartRepository.findCartByUser(user);
    }

    @Override
    public Cart createCart(User user, double value, OrderItemRequest item) {
        Cart cart = new Cart();
        cart.setTotalAmount(value);
        cart.setUser(user);

        List<OrderItemRequest> items = new ArrayList<>();
        items.add(item);
        cartItems.put(user.getId(), items);
        return cartRepository.save(cart);
    }

    @Override
    public void updateCartAmount(int cartId, double value) {
        Cart cart = cartRepository.findCartById(cartId);
        cart.setTotalAmount(value);
        cartRepository.save(cart);
    }

    @Override
    public void resetCart(Cart cart) {
        cart.setTotalAmount(0);
        cartItems.put(cart.getUser().getId(), new ArrayList<>());
        cartRepository.save(cart);
    }

    private void addItemToCart(User user, OrderItemRequest item) {
        List<OrderItemRequest> items = cartItems.get(user.getId());
        int index = IntStream.range(0, items.size())
                .filter(i -> items.get(i).getProduct().getId() == item.getProduct().getId())
                .findFirst().orElse(-1);

        if (index != -1) { // item already exists in lists, only add
            OrderItemRequest old = items.get(index);

            // update quantity of item in customer's list
            items.set(index, new OrderItemRequest(old.getProduct(), old.getQuantity() + item.getQuantity(), old.getPrice()));

        } else {
            items.add(item);
        }
        cartItems.put(user.getId(), items);
    }

    @Override
    public OrderItemRequest getItemByProductId(int productId, int userId) {
        List<OrderItemRequest> items = cartItems.get(userId);
        int index = IntStream.range(0, items.size())
                .filter(i -> items.get(i).getProduct().getId() == productId)
                .findFirst().orElse(-1);
        return items.get(index);
    }

    @Override
    public int updateItemQuantity(int userId, OrderItemRequest item, int quantity) {
        List<OrderItemRequest> items = cartItems.get(userId);
        int index = IntStream.range(0, items.size())
                .filter(i -> items.get(i).getProduct().getId() == item.getProduct().getId())
                .findFirst().orElse(-1);
        if (index != -1) {
            OrderItemRequest itemFound = items.get(index);
            int oldQuantity = itemFound.getQuantity();
            itemFound.setQuantity(quantity);
            items.set(index, itemFound);
            cartItems.put(userId, items);
            return oldQuantity;
        }
        return -1;
    }

    @Override
    public List<OrderItemRequest> deleteItemFromCart(Cart cart, int userId, int productId) {

        productService.findProductById(productId);

        List<OrderItemRequest> items = cartItems.get(userId);

        int index = IntStream.range(0, items.size())
                .filter(i -> items.get(i).getProduct().getId() == productId)
                .findFirst().orElse(-1);

        if (index == -1) {
            throw new ProductNotInCartException(productId);
        }
        OrderItemRequest item = items.get(index);

        updateCartAmount(cart.getId(), cart.getTotalAmount() - (item.getQuantity() * item.getPrice()));
        items.remove(index);
        cartItems.put(userId, items);
        return items;
    }

    @Override
    public List<OrderItemRequest> getCartContents(int userId) {
        if (getCartItems().get(userId) == null)
            // throw new CartIsEmptyException(userId);
            return new ArrayList<>();
        else return cartItems.get(userId);
    }

    public Map<Integer, List<OrderItemRequest>> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Integer, List<OrderItemRequest>> items) {
        this.cartItems = items;
    }

    @Override
    public Product validateProduct(int productId, int quantity) {
        Product product = productService.findProductById(productId);

        if (quantity <= 0)
            throw new NegativeQuantityException();
        if (product.getStock() < quantity) {
            throw new ProductNotInStockException(productId);
        }
        return product;
    }

    private void addToCartAmount(int cartId, double value) {
        Cart cart = cartRepository.findCartById(cartId);
        cart.setTotalAmount(cart.getTotalAmount() + value);
        cartRepository.save(cart);
    }

    @Override
    public Cart addProductToCart(User user, OrderItemRequest item) {
        Cart cart = findCartByUser(user);

        if (cart == null) {
            // if there is no existing cart for the user, we create one, also initializing the amount with the product added
            cart = createCart(user, item.getQuantity() * item.getPrice(), item);

        } else {
            addItemToCart(user, item);
            addToCartAmount(cart.getId(), item.getQuantity() * item.getPrice());
        }
        return cart;
    }
}
