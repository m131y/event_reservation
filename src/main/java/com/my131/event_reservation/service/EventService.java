package com.my131.event_reservation.service;

import com.my131.event_reservation.dto.EventDto;
import com.my131.event_reservation.model.Event;
import com.my131.event_reservation.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepository eventRepository;

    public Event getById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new NoSuchElementException("이벤트없음"));
    }

    public Page<Event> search(String name, String location, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        Specification<Event> specification = Specification.allOf();
        //callback : WHERE절을 실행, WHERE name LIKE '%단어%' = '단어'가 포함되어 있는 데이터
        if (name != null)
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        if (location != null)
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("location"), "%" + location + "%"));
        if (from != null)
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), from)));
        if (to != null)
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), to)));

        return eventRepository.findAll(specification, pageable);
    }
    public Event create(EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setEventDate(eventDto.getEventDate());
        event.setLocation(eventDto.getLocation());

        return eventRepository.save(event);
    }

    public Event update(Long id, EventDto eventDto) {
        Event existEvent = getById(id);
        existEvent.setName(eventDto.getName());
        existEvent.setEventDate(eventDto.getEventDate());
        existEvent.setLocation(eventDto.getLocation());

        return eventRepository.save(existEvent);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
