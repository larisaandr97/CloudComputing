package com.javaproject.storeapp.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.javaproject.storeapp.entity.Pattern.EMAIL_ADDRESS;

@Data
public class UserRequest {

    @NotBlank
    @Length(min = 8, max = 20)
    private String username;

    @NotBlank
    @Length(max = 100)
    private String firstName;

    @NotBlank
    @Length(max = 100)
    private String lastName;

    @NotBlank
    @Length(min = 8, max = 100)
    private String password;

    @NotBlank
    @Pattern(regexp = EMAIL_ADDRESS)
    @Length(max = 100)
    private String email;

    public UserRequest(String username, String firstName, String lastName, String password, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }
}
