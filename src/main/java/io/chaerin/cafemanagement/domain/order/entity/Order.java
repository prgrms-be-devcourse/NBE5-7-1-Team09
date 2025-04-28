package io.chaerin.cafemanagement.domain.order.entity;

import io.chaerin.cafemanagement.domain.user.entity.User;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "create_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public static Order create(User user, String email, String address, String postCode) {
        Order order = new Order();
        order.user = user;
        order.email = email;
        order.address = address;
        order.postCode = postCode;
        order.createdAt = LocalDateTime.now(); // 명시적으로 초기화
        order.orderItemList = new ArrayList<>(); // 명시적으로 초기화
        return order;
    }

    public void fixAddress(String address, String postCode, String email) {
        this.email = email;
        this.address = address;
        this.postCode = postCode;
    }

    public void clearItems() {
        this.orderItemList.clear();
    }

    public void addItem(OrderItem orderItem) {
        this.orderItemList.add(orderItem);
    }

    public Integer calculateTotalPrice() {
        return orderItemList.stream()
                .mapToInt(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
