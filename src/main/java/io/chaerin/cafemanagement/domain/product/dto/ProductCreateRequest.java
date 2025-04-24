package io.chaerin.cafemanagement.domain.product.dto;

import io.chaerin.cafemanagement.domain.product.entity.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotNull(message = "이름은 필수입니다.")
    @Size(min = 1, max = 100, message = "상품 이름은 100자를 초과할 수 없습니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    private Integer price;

    @Size(max = 255, message = "URL은 255자를 초과할 수 없습니다.")
    private String imageUrl;

    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .imageUrl(this.imageUrl)
                .build();
    }
}