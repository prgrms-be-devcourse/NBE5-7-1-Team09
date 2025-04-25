package io.chaerin.cafemanagement.domain.question.service.utils;

import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.product.entity.Product;
import io.chaerin.cafemanagement.domain.product.repository.ProductRepository;

import java.util.List;

public class TestUtils {
    public static void createProduct(ProductRepository productRepository) {
        productRepository.save(
                Product.builder()
                        .name("1번콩")
                        .price(1000)
                        .imageUrl("/url1")
                        .build()
        );
        productRepository.save(
                Product.builder()
                        .name("2번콩")
                        .price(2000)
                        .imageUrl("/url2")
                        .build()
        );
        productRepository.save(
                Product.builder()
                        .name("3번콩")
                        .price(3000)
                        .imageUrl("/url3")
                        .build()
        );

    }

    public static void createOrders() {

        TestOrderRequestDto o1 = new TestOrderRequestDto(
                "test1@test.com",
                "address1",
                "12345",
                List.of(
                        new TestOrderItems(1L, 2),
                        new TestOrderItems(2L, 1)
                )
        );

        TestOrderRequestDto o2 = new TestOrderRequestDto(
                "test1@test.com",
                "address1",
                "12345",
                List.of(
                        new TestOrderItems(1L, 3),
                        new TestOrderItems(3L, 1)
                )
        );

        TestOrderRequestDto o3 = new TestOrderRequestDto(
                "test3@test.com",
                "address3",
                "33333",
                List.of(
                        new TestOrderItems(1L, 2),
                        new TestOrderItems(2L, 1)
                )
        );
    }


}