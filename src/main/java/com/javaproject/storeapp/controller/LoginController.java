package com.javaproject.storeapp.controller;

import com.javaproject.storeapp.dto.UserRequest;
import com.javaproject.storeapp.entity.Role;
import com.javaproject.storeapp.entity.User;
import com.javaproject.storeapp.mapper.UserMapper;
import com.javaproject.storeapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {

    private final UserMapper userMapper;
    private final UserService userService;

    public LoginController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginForm(Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());
        try {
            Object flash = request.getSession().getAttribute("flash");
            model.addAttribute("flash", flash);

            request.getSession().removeAttribute("flash");
        } catch (Exception ex) {
        }
        return "login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String registerForm(Model model, HttpServletRequest request) {
        model.addAttribute("userRequest", new User());
        try {
            Object flash = request.getSession().getAttribute("flash");
            model.addAttribute("flash", flash);

            request.getSession().removeAttribute("flash");
        } catch (Exception ex) {
        }
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid
                                 @RequestBody
                                 @ModelAttribute
                                         UserRequest userRequest,
                                 BindingResult bindingResult) {
        ModelAndView viewRegister = new ModelAndView("register");
        if (bindingResult.hasErrors()) {
            return viewRegister;
        }
        User user = userMapper.userRequestToUser(userRequest);
        user.setRole(new Role(1, "ROLE_USER"));
        user.setEnabled(true);
        userService.registerNewUser(user);
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }


    @RequestMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }
}
