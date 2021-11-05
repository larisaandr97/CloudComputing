package com.javaproject.storeapp.mapper;

import com.javaproject.storeapp.dto.UserRequest;
import com.javaproject.storeapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User userRequestToUser(UserRequest userRequest) {
        return new User(userRequest.getUsername(), passwordEncoder.encode(userRequest.getPassword()), userRequest.getEmail(), userRequest.getFirstName(), userRequest.getLastName());
    }
}
