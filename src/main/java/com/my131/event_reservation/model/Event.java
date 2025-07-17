package com.my131.event_reservation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    // 기본키
    // 생성전략 : 자동생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DB단에서 null 방지 (물리적 제약)
    @Column(nullable = false)
    private String name;

    // DB단에서 null 방지 (물리적 제약)
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    private String location;

    // 일대다관계, mappedBy:이 필드는 관계의 주인이 아님, 상대 엔티티의 event필드가 주인.
    // cascade = CascadeType.ALL : 연관된 자식 엔티티도 동일한 작업을 함께 자동으로 수행
    // orphanRemoval = true도 함께 설정해야 자식 고아 객체도 확실히 제거
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    // 자바 직렬화 순환 방지 대책, 이 필드는 직렬화 대상.
    @JsonManagedReference
    private List<Reservation> reservations = new ArrayList<>();
}
