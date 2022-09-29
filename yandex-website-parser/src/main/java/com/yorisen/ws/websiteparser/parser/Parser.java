package com.yorisen.ws.websiteparser.parser;

import com.yorisen.ws.iteventsaggregator.dto.Event;

import java.util.List;

public interface Parser {
    List<Event> parseEvents();
}
