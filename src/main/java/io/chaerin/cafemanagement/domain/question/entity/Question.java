package io.chaerin.cafemanagement.domain.question.entity;

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

    private Long orderId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT")
    private String answer;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime answerCreatedAt;

    @Builder
    public Question(Long orderId, String title, String content) {
        this.orderId = orderId;
        this.title = title;
        this.content = content;
    }



}
