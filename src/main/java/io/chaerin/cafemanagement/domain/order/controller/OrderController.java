package io.chaerin.cafemanagement.domain.order.controller;

import io.chaerin.cafemanagement.domain.order.dto.OrderCreateRequestDto;
import io.chaerin.cafemanagement.domain.order.dto.OrderResponseDto;
import io.chaerin.cafemanagement.domain.order.dto.OrderUpdateRequestDto;
import io.chaerin.cafemanagement.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    public String saveOrder(@ModelAttribute OrderCreateRequestDto request, Model model) {
        OrderResponseDto order = orderService.saveOrder(request);
        model.addAttribute("order", order);
        // 임의지정
        return "order/result";
    }

    @GetMapping
    public String getOrdersByEmail(@RequestParam String email, Model model) {
        List<OrderResponseDto> orders = orderService.getOrdersByEmail(email);
        if (orders.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("orders", orders);
        // 임의지정
        return "order/list";
    }

    @PutMapping("/{id}")

    public String updateOrder(@PathVariable Long id, @ModelAttribute OrderUpdateRequestDto request, Model model) {
        OrderResponseDto updatedOrder = orderService.updateOrder(id, request);
        model.addAttribute("order", updatedOrder);
        // 임의지정
        return "order/result";
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        // 임의지정
        return "redirect:/orders";
    }
}
