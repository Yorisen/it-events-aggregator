package com.yorisen.ws.iteventsaggregator.controllers;

import com.yorisen.ws.iteventsaggregator.exceptions.NotFoundException;
import com.yorisen.ws.iteventsaggregator.models.Event;
import com.yorisen.ws.iteventsaggregator.models.Speaker;
import com.yorisen.ws.iteventsaggregator.services.SpeakerService;
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
public class SpeakerController {
    private final SpeakerService speakerService;

    @GetMapping("/speakers")
    public List<Speaker> getSpeakers() {
        List<Speaker> speakers = speakerService.findAll();
        speakers.forEach(speaker -> speaker.setLectures(new ArrayList<>()));

        return speakers;
    }

    @GetMapping("/speaker/{id}")
    public Speaker getSpeakerById(@PathVariable("id") BigDecimal id) {
        Optional<Speaker> speaker = speakerService.findById(id);
        if (speaker.isPresent()) {
            Speaker speakerObj = speaker.get();
            speakerObj.getLectures().size();
        }
        return speaker.orElseThrow(NotFoundException::new);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Speaker not found!");
    }
}
