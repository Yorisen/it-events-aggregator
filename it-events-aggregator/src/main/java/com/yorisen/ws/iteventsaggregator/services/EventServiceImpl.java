package com.yorisen.ws.iteventsaggregator.services;

import com.yorisen.ws.iteventsaggregator.converters.EventConverter;
import com.yorisen.ws.iteventsaggregator.models.Event;
import com.yorisen.ws.iteventsaggregator.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventConverter eventConverter;

    @Override
    public List<Event> findAll() {
        return eventRepository.findAllByOrderByDateDesc();
    }

    @Override
    public Optional<Event> findById(BigDecimal id) {
        return eventRepository.findById(id);
    }

    @Override
    @Transactional
    public Event save(com.yorisen.ws.iteventsaggregator.dto.Event dtoEvent) {
        Optional<Event> existingEvent = eventRepository.findByNameAndDate(dtoEvent.getName(), dtoEvent.getDate());
        return existingEvent.orElseGet(() -> eventRepository.save(eventConverter.convert(dtoEvent)));
    }

    @Override
    @Transactional
    public List<Event> save(List<com.yorisen.ws.iteventsaggregator.dto.Event> dtoEvents) {
        List<Event> events = new ArrayList<>();
        if (dtoEvents != null) {
            for (com.yorisen.ws.iteventsaggregator.dto.Event dtoEvent: dtoEvents) {
                events.add(save(dtoEvent));
            }
        }

        return events;
    }
}
