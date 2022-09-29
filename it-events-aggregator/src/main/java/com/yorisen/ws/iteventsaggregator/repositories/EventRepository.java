package com.yorisen.ws.iteventsaggregator.repositories;

import com.yorisen.ws.iteventsaggregator.models.Event;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, BigDecimal> {
    @Override
    @EntityGraph(attributePaths = {"lectures"})
    Optional<Event> findById(BigDecimal id);

    List<Event> findAllByOrderByDateDesc();

    Optional<Event> findByNameAndDate(String name, LocalDate date);
}
