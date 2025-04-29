package io.chaerin.cafemanagement.domain.order.controller;

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

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class OrderAdminController {

    private final OrderService orderService;

    @GetMapping()
    public String getAllOrders(Model model, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<OrderResponseDto> orders = orderService.getAllOrders(pageable);
        if (orders.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("orders", orders);
        return "order/adminList";
    }

    @PutMapping("/{id}")
    public String updateOrderAdmin(@PathVariable Long id, @ModelAttribute OrderUpdateRequestDto request, Model model) {
        OrderResponseDto updatedOrder = orderService.updateOrder(id, request);
        model.addAttribute("order", updatedOrder);
        // 임의지정
        return "redirect:/admin/orders";
    }

    @DeleteMapping("/{id}")
    public String deleteOrderAdmin(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/admin/orders";
    }
}
