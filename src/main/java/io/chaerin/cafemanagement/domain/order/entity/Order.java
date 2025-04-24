package io.chaerin.cafemanagement.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String email;

    private String address;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "create_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public static Order create(String email, String address, String postCode) {
        Order order = new Order();
        order.email = email;
        order.address = address;
        order.postCode = postCode;
        order.createdAt = LocalDateTime.now(); // 명시적으로 초기화
        order.orderItemList = new ArrayList<>(); // 명시적으로 초기화
        return order;
    }

    public void fixAddress(String address, String postCode) {
        this.address = address;
        this.postCode = postCode;
    }

    public void clearItems() {
        this.orderItemList.clear();
    }

    public void addItem(OrderItem orderItem) {
        this.orderItemList.add(orderItem);
    }

}
