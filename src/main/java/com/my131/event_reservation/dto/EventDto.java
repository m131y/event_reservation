package com.my131.event_reservation.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

    @NotNull(message = "날짜를 입력해주세요.")
    private LocalDateTime eventDate;

    private String location;
}
