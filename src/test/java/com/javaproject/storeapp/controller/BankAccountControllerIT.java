package com.javaproject.storeapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaproject.storeapp.dto.BankAccountRequest;
import com.javaproject.storeapp.entity.BankAccount;
import com.javaproject.storeapp.entity.Role;
import com.javaproject.storeapp.entity.User;
import com.javaproject.storeapp.mapper.BankAccountMapper;
import com.javaproject.storeapp.service.UserService;
import com.javaproject.storeapp.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BankAccountController.class)
//@WithMockUser(username = "user", roles = "USER")
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
public class BankAccountControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BankAccountServiceImpl bankAccountService;
    @MockBean
    private UserService userService;
    @MockBean
    private BankAccountMapper bankAccountMapper;

    @Test
    @DisplayName("Create a new Bank Account")
    //@WithMockUser(roles = "USER")
    public void createBankAccountHappyFlow() throws Exception {
        User user = new User();
        user.setId(1);
        user.setEnabled(true);
        user.setRole(new Role(1, "USER"));
        user.setUsername("user");
        BankAccountRequest request = new BankAccountRequest("3331965465", 200, "4331256148952346", user);

        when(bankAccountService.createBankAccount(any())).thenReturn(new BankAccount(1, "3331965465", 200, "4331256148952346", user));
        when(userService.findByUsername(user.getUsername())).thenReturn(user);

        mockMvc.perform(post("/accounts/" + user.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
                .with(csrf())
                .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isCreated());
    }

}
