package com.javaproject.storeapp.dto;


import com.javaproject.storeapp.entity.User;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.javaproject.storeapp.entity.Pattern.VISA_CREDIT_CARD;

@Data
public class BankAccountRequest {

    @NotBlank(message = "Account number cannot be null.")
    private String accountNumber;

    @NotNull
    @Min(0)
    private double balance;

    @Pattern(regexp = VISA_CREDIT_CARD, message = "Card number not valid! Only Visa accepted.")
    private String cardNumber;

    // @JsonIgnore
    private User user;

    public BankAccountRequest() {
    }

    public BankAccountRequest(String accountNumber, double balance, String cardNumber, User user) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.user = user;
    }
}
