package com.javaproject.storeapp.service;

import com.javaproject.storeapp.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);

    void registerNewUser(User user);

}
