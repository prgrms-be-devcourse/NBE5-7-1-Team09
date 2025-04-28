package io.chaerin.cafemanagement.domain.product.controller;

import io.chaerin.cafemanagement.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("productResponseDtos", productService.getAllProducts());
        return "product/cart";
    }
}