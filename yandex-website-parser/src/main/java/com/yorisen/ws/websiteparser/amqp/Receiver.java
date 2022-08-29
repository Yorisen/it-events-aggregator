package com.yorisen.ws.websiteparser.amqp;

import com.yorisen.ws.iteventsaggregator.dto.Event;
import com.yorisen.ws.websiteparser.parser.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class Receiver {
    private final Parser parser;
    private final RabbitTemplate rabbitTemplate;

    @Value("${amqp.response.exchange}")
    private String exchangeName;

    @Value("${amqp.response.routing-key}")
    private String routingKey;

    public void receiveMessage(String message) {
        System.out.println("Received - \"" + message + "\"");
    }

    public void receiveMessage(byte[] message) {
        System.out.println("Received - \"" + new String(message) + "\"");
        List<Event> events = parser.parseEvents();
        events.forEach(event -> log.info(event.toString()));

        rabbitTemplate.convertAndSend(exchangeName, routingKey, events);
    }
}
