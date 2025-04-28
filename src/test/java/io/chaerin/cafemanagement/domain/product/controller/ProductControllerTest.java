package io.chaerin.cafemanagement.domain.product.controller;

import io.chaerin.cafemanagement.domain.product.dto.ProductCreateRequest;
import io.chaerin.cafemanagement.domain.product.dto.ProductResponse;
import io.chaerin.cafemanagement.domain.product.dto.ProductUpdateRequest;
import io.chaerin.cafemanagement.domain.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    // 1. GET /products (목록 조회)
    @Test
    void listProducts_ReturnsProductList() throws Exception {
        // Given
        List<ProductResponse> products = List.of(
                ProductResponse.builder().name("Coffee").price(5000).imageUrl("coffee.jpg").build(),
                ProductResponse.builder().name("Tea").price(3000).imageUrl("tea.jpg").build()
        );
        when(productService.getAllProducts()).thenReturn(products);

        // When & Then
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/list"))
                .andExpect(model().attribute("productResponseDtos", products));
    }

    // 2. GET /products/new (생성 폼)
    @Test
    void showCreateForm_ReturnsFormView() throws Exception {
        // When & Then
        mockMvc.perform(get("/products/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/form"))
                .andExpect(model().attributeExists("productForm"));
    }

    // 3. POST /products (제품 생성 - 성공)
    @Test
    void createProduct_RedirectsToList_WhenValid() throws Exception {
        // Given
        ProductResponse savedProduct = ProductResponse.builder()
                .name("Coffee").price(5000).imageUrl("coffee.jpg").build();
        when(productService.saveProduct(any(ProductCreateRequest.class))).thenReturn(savedProduct);

        // When & Then
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Coffee")
                        .param("price", "5000")
                        .param("imageUrl", "coffee.jpg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }

    // 4. POST /products (제품 생성 - 유효성 검사 실패)
    @Test
    void createProduct_ReturnsForm_WhenInvalid() throws Exception {
        // When & Then
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "") // @NotNull 위반
                        .param("price", "")) // @NotNull 위반
                .andExpect(status().isOk())
                .andExpect(view().name("product/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("productForm", "name", "price"));
    }

    // 5. GET /products/{id}/edit (수정 폼)
    @Test
    void showUpdateForm_ReturnsFormView() throws Exception {
        // Given
        ProductResponse product = ProductResponse.builder()
                .name("Coffee").price(5000).imageUrl("coffee.jpg").build();
        when(productService.getProductById(1L)).thenReturn(product);

        // When & Then
        mockMvc.perform(get("/products/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/form"))
                .andExpect(model().attributeExists("productForm"))
                .andExpect(model().attribute("productId", 1L));
    }

    // 6. POST /products/{id} (제품 수정 - 성공)
    @Test
    void updateProduct_RedirectsToList_WhenValid() throws Exception {
        // Given
        ProductResponse updatedProduct = ProductResponse.builder()
                .name("Updated Coffee").price(6000).imageUrl("updated.jpg").build();
        when(productService.updateProduct(eq(1L), any(ProductUpdateRequest.class))).thenReturn(updatedProduct);

        // When & Then
        mockMvc.perform(post("/products/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Updated Coffee")
                        .param("price", "6000")
                        .param("imageUrl", "updated.jpg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }

    // 7. POST /products/{id} (제품 수정 - 유효성 검사 실패)
    @Test
    void updateProduct_ReturnsForm_WhenInvalid() throws Exception {
        // When & Then
        mockMvc.perform(post("/products/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "") // @Size 위반
                        .param("price", "6000"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("productForm", "name"))
                .andExpect(model().attribute("productId", 1L));
    }

    // 8. DELETE /products/{id} (제품 삭제)
    @Test
    void deleteProduct_RedirectsToList() throws Exception {
        // Given
        doNothing().when(productService).deleteProduct(1L);

        // When & Then
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }
}