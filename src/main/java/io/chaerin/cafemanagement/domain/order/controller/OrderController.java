package io.chaerin.cafemanagement.domain.order.controller;

import io.chaerin.cafemanagement.domain.order.dto.OrderUpdateRequestDto;
import io.chaerin.cafemanagement.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Object> saveOrder(@RequestBody OrderUpdateRequestDto request) {
        return ResponseEntity.ok(orderService.saveOrder(request));
    }

    @GetMapping
    public ResponseEntity<Object> getOrdersByEmail(@RequestParam String email) {
        return ResponseEntity.ok(orderService.getOrdersByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody OrderUpdateRequestDto request) {
        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
