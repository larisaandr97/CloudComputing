package com.javaproject.storeapp.controller;

import com.javaproject.storeapp.entity.Order;
import com.javaproject.storeapp.entity.User;
import com.javaproject.storeapp.service.OrderService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ModelAndView getOrderById(@PathVariable int id) {
        Order order = orderService.findOrderById(id);
        ModelAndView modelAndView = new ModelAndView("orderDetails");
        modelAndView.addObject("order", order);
        modelAndView.addObject("items", order.getOrderItems());
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getOrdersByUser(Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        ModelAndView modelAndView = new ModelAndView("orders");
        List<Order> ordersFound = orderService.getOrdersByUser(user);
        modelAndView.addObject("orders", ordersFound);
        return modelAndView;
    }
}
