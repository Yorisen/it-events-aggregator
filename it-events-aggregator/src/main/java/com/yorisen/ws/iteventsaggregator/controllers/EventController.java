package com.yorisen.ws.iteventsaggregator.controllers;

import com.yorisen.ws.iteventsaggregator.exceptions.NotFoundException;
import com.yorisen.ws.iteventsaggregator.models.Event;
import com.yorisen.ws.iteventsaggregator.services.EventService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("/events")
    public List<Event> getEvents() {
        List<Event> events = eventService.findAll();
        events.forEach(event -> event.setLectures(new ArrayList<>()));
        return events;
    }

    @GetMapping("/event/{id}")
    public Event getEventById(@PathVariable("id") BigDecimal id) {
        Optional<Event> event = eventService.findById(id);
        if (event.isPresent()) {
            Event eventObj = event.get();
            eventObj.getLectures().size();
        }
        return event.orElseThrow(NotFoundException::new);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Event not found!");
    }
}
