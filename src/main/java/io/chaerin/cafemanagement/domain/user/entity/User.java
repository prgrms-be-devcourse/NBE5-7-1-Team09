package io.chaerin.cafemanagement.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

}
