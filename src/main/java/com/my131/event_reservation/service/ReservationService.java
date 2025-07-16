package com.my131.event_reservation.service;

import com.my131.event_reservation.dto.ReservationDto;
import com.my131.event_reservation.model.Event;
import com.my131.event_reservation.model.Reservation;
import com.my131.event_reservation.repository.EventRepository;
import com.my131.event_reservation.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;

    public Page<Reservation> getByEvent(Long eventId, Pageable pageable) {
        return reservationRepository.findByEventId(eventId, pageable);
    }

    public Reservation getById(Long id) {
        return reservationRepository.findById(id).orElseThrow(()-> new NoSuchElementException("해당 예약이 없습니다."));
    }

    public Reservation create(Long eventId, ReservationDto reservationDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(()-> new NoSuchElementException("이벤트없음"));

        Reservation reservation = new Reservation();
        reservation.setEvent(event);
        reservation.setAttendeeName(reservationDto.getAttendeeName());
        reservation.setSeat(reservationDto.getSeats());

        return reservationRepository.save(reservation);
    }

    public Reservation update(Long id, ReservationDto reservationDto){
        Reservation existReservation = getById(id);

        existReservation.setAttendeeName(reservationDto.getAttendeeName());
        existReservation.setSeat(reservationDto.getSeats());

        return reservationRepository.save(existReservation);
    }

    public void delete(Long id){
        reservationRepository.deleteById(id);
    }
}
