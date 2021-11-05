package com.javaproject.storeapp.repository;

import com.javaproject.storeapp.entity.Order;
import com.javaproject.storeapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderById(int id);

    default List<Order> findOrdersByUser(User user) {
        return this.findAll().stream().filter(order -> order.getUser().getId() == user.getId()).collect(Collectors.toList());
    }
}
