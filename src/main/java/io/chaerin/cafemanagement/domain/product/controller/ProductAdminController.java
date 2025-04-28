package io.chaerin.cafemanagement.domain.product.controller;

import io.chaerin.cafemanagement.domain.product.dto.ProductCreateRequest;
import io.chaerin.cafemanagement.domain.product.dto.ProductResponse;
import io.chaerin.cafemanagement.domain.product.dto.ProductUpdateRequest;
import io.chaerin.cafemanagement.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("productForm", new ProductCreateRequest());
        return "product/form";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute("productForm") ProductCreateRequest request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "product/form";
        }
        productService.saveProduct(request);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        ProductResponse product = productService.getProductById(id);
        ProductUpdateRequest updateRequest = new ProductUpdateRequest();
        updateRequest.setName(product.getName());
        updateRequest.setPrice(product.getPrice());
        updateRequest.setImageUrl(product.getImageUrl());
        model.addAttribute("productForm", updateRequest);
        model.addAttribute("productId", id);
        return "product/form";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("productForm") ProductUpdateRequest request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productId", id);
            return "product/form";
        }
        productService.updateProduct(id, request);
        return "redirect:/admin/products/list";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/list")
    public String listAdminProducts(Model model) {
        model.addAttribute("productResponseDtos", productService.getAllProducts());
        return "product/list";
    }
}