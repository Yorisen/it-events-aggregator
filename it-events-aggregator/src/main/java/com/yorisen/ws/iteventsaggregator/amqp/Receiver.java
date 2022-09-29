package com.yorisen.ws.iteventsaggregator.amqp;

import com.yorisen.ws.iteventsaggregator.dto.Event;
import com.yorisen.ws.iteventsaggregator.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class Receiver {
    private final EventService eventService;

    public void receiveMessage(String message) {
        System.out.println("Received - \"" + message + "\"");
    }

    public void receiveMessage(List<Event> events) {
        log.info("Received - \"{}\"", events);
        eventService.save(events);
    }

    public void receiveMessage(byte[] message) {
        System.out.println("Received - \"" + new String(message) + "\"");
    }

}
