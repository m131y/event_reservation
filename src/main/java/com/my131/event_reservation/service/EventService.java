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

    // 단일 조회
    public Event getById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new NoSuchElementException("이벤트없음"));
    }

    // 검색조회 + 페이징
    // 검색에 사용된 변수, 페이징에 필요한 pageable 정보를 매개변수로 받음
    public Page<Event> search(String name, String location, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        // 검색을 위해 빈 스펙 객체를 생성, allof() = > and 조건, 모두 만족해야 함
        Specification<Event> specification = Specification.allOf();
        // name 입력이 null이 아닐 경우
        if (name != null)
            // 스팩 객체에 and 조건 추가
            // root.get("name") : Event 객체의 name 필드
            // criterialBuilder.like(...) : SQL에서 LIKE 연산자에 해당
            // 즉, 이 람다식은 WHERE name LIKE "%name 입력값%"
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        // location 입력이 null이 아닐 경우
        if (location != null)
            // 스팩 객체에 and 조건 추가
            // root.get("location") : Event 객체의 location 필드
            // criterialBuilder.like(...) : SQL에서 LIKE 연산자에 해당
            // 즉, 이 람다식은 WHERE location LIKE "%location 입력값%"
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("location"), "%" + location + "%"));
        // from 입력이 null이 아닐 경우
        if (from != null)
            // 스팩 객체에 and 조건 추가
            // root.get("eventDate") : Event 객체의 eventDate 필드
            // criterialBuilder.greaterThanOrEqualTo(...) : SQL에서 >= 연산자에 해당
            // 즉, 이 람다식은 WHERE eventDate >= from
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), from)));
        // to 입력이 null이 아닐 경우
        if (to != null)
            // 스팩 객체에 and 조건 추가
            // root.get("eventDate") : Event 객체의 eventDate 필드
            // criterialBuilder.lessThanOrEqualTo(...) : SQL에서 <= 연산자에 해당
            // 즉, 이 람다식은 WHERE eventDate >= to
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), to)));

        // 조건이 모두 누적된 Specification을 전달하여 조건에 따라 필터링 + 페이징 처리된 Page<Event> 객체를 리턴
        return eventRepository.findAll(specification, pageable);
    }

    // 이벤트 생성
    public Event create(EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setEventDate(eventDto.getEventDate());
        event.setLocation(eventDto.getLocation());

        return eventRepository.save(event);
    }

    // 이벤트 수정
    public Event update(Long id, EventDto eventDto) {
        Event existEvent = getById(id);
        existEvent.setName(eventDto.getName());
        existEvent.setEventDate(eventDto.getEventDate());
        existEvent.setLocation(eventDto.getLocation());

        return eventRepository.save(existEvent);
    }

    // 이벤트 삭제
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
