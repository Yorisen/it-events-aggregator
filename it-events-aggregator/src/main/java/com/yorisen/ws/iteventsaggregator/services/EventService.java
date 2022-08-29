package com.yorisen.ws.iteventsaggregator.services;

import com.yorisen.ws.iteventsaggregator.models.Event;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> findAll();
    Optional<Event> findById(BigDecimal id);
    Event save(com.yorisen.ws.iteventsaggregator.dto.Event dtoEvent);

    List<Event> save(List<com.yorisen.ws.iteventsaggregator.dto.Event> dtoEvents);
}
