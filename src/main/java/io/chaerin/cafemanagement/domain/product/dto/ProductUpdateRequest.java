package io.chaerin.cafemanagement.domain.product.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateRequest {

    @Size(min = 1, max = 100, message = "상품 이름은 100자를 초과할 수 없습니다.")
    private String name;

    private Integer price;

    @Size(max = 255, message = "URL은 255자를 초과할 수 없습니다.")
    private String imageUrl;

    @Builder
    public ProductUpdateRequest(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}