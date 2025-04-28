package io.chaerin.cafemanagement.domain.order.controller;

import io.chaerin.cafemanagement.domain.order.dto.OrderCreateRequestDto;
import io.chaerin.cafemanagement.domain.order.dto.OrderResponseDto;
import io.chaerin.cafemanagement.domain.order.dto.OrderUpdateRequestDto;
import io.chaerin.cafemanagement.domain.order.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public String saveOrder(@ModelAttribute OrderCreateRequestDto request, Model model, HttpSession session) {
        OrderResponseDto order = orderService.saveOrder(request, session);
        model.addAttribute("order", order);
        // 임의지정
        return "order/result";
    }

    @GetMapping
    public String getOrdersById(HttpSession session, Model model) {
        List<OrderResponseDto> orders = orderService.getOrdersById(session);
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
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);

        return "redirect:/orders";
    }

    @GetMapping("/admin")
    public String getAllOrders(HttpSession session,Model model, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<OrderResponseDto> orders = orderService.getAllOrders(pageable);
        if(orders.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("orders", orders);
        return "order/adminList";
    }

    @PutMapping("/admin/{id}")
    public String updateOrderAdmin(@PathVariable Long id, @ModelAttribute OrderUpdateRequestDto request, Model model) {
        OrderResponseDto updatedOrder = orderService.updateOrder(id, request);
        model.addAttribute("order", updatedOrder);
        // 임의지정
        return "redirect:/orders/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteOrderAdmin(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders/admin";
    }
}
