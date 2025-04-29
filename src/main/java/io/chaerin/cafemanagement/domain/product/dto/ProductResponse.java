package io.chaerin.cafemanagement.domain.product.dto;

import io.chaerin.cafemanagement.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

    private final Long productId;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl() != null ? product.getImageUrl() : "https://i.imgur.com/HKOFQYa.jpeg")
                .build();
    }

}