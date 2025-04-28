package io.chaerin.cafemanagement.domain.question.entity;

import io.chaerin.cafemanagement.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT")
    private String answer;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime answerCreatedAt;

    @Builder
    public Question(Order order, String title, String content) {
        this.order = order;
        this.title = title;
        this.content = content;
    }

    public void writeAnswer(String answer) {
        this.answer = answer;
        this.answerCreatedAt = LocalDateTime.now();
    }


}
