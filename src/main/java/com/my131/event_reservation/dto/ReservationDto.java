package com.my131.event_reservation.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.my131.event_reservation.model.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    // 요청 단계에서 null 필터링 (논리적 제약)
    @NotBlank(message = "이름을 입력해 주세요.")
    private String attendeeName;

    @Min(1)	 // 최소 1 제약
    // 요청 단계에서 null 필터링 (논리적 제약)
    @NotNull(message = "좌석수를 입력해 주세요.")
    private int seats;


}
