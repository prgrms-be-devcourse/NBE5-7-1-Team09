package io.chaerin.cafemanagement.domain.order.entity;

import io.chaerin.cafemanagement.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer quantity;

    public static OrderItem create(Product product, Order order, int quantity) {
        OrderItem oi = new OrderItem();
        oi.product = product;
        oi.order = order;
        oi.quantity = quantity;
        return oi;
    }

}
