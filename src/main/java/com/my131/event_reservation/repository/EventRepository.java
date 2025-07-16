package com.my131.event_reservation.repository;

import com.my131.event_reservation.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//interface는 다중상속 가능, class는 안됨
//JpaRepository와 JpaSpecificationExecutor(검색 기능을 위함)을 다중 상속
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

}
