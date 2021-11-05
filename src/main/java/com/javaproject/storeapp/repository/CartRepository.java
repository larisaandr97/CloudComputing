package com.javaproject.storeapp.repository;

import com.javaproject.storeapp.entity.Cart;
import com.javaproject.storeapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findCartById(int id);

    Cart findCartByUser(User user);
}
