package io.chaerin.cafemanagement.domain.question.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private Long orderId;   // Order의 내용을 사용 안할 것 같아, 연관관계 설정 안했습니다..!

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public Question(Long orderId, String title, String content) {
        this.orderId = orderId;
        this.title = title;
        this.content = content;
    }
}
