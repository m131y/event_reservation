package com.my131.event_reservation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    // 기본키
    // 생성전략 : 자동생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String attendeeName;

    private int seat;

    // 다대일관계
    // fetch = FetchType.LAZY : 지연 로딩 전략 설정, event는 실제로 사용할 때까지 DB에서 조회하지 않음
    @ManyToOne(fetch = FetchType.LAZY)
    // event_id라는 외래 키(FK) 컬럼을 생성
    @JoinColumn(name = "event_id", nullable = false)
    // 자바 직렬화 순환 방지 대책, 이 필드는 직렬화 제외.
    @JsonBackReference
    private Event event;
}
