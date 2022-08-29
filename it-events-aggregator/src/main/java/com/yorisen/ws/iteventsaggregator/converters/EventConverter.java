package com.yorisen.ws.iteventsaggregator.converters;

import com.yorisen.ws.iteventsaggregator.models.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventConverter {
    private final LectureConverter lectureConverter;

    public List<Event> convert(List<com.yorisen.ws.iteventsaggregator.dto.Event> dtoEvents) {
        List<Event> events = new ArrayList<>();
        if (dtoEvents != null) {
            for (com.yorisen.ws.iteventsaggregator.dto.Event dtoEvent : dtoEvents) {
                events.add(convert(dtoEvent));
            }
        }

        return events;
    }

    public Event convert(com.yorisen.ws.iteventsaggregator.dto.Event dtoEvent) {
        Event event = new Event();
        event.setName(dtoEvent.getName());
        event.setDescription(dtoEvent.getDescription());
        event.setDate(dtoEvent.getDate());
        event.setCity(dtoEvent.getCity());
        event.setLink(dtoEvent.getLink());
        event.setLectures(lectureConverter.convert(dtoEvent.getLectures(), event));

        return event;
    }
}
