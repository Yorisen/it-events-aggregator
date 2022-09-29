package com.yorisen.ws.iteventsaggregator.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class Event implements Serializable {
    @Serial
    private static final long serialVersionUID = 6423191241057024177L;
    private String name;
    private String description;
    private LocalDate date;
    private String city;
    private String link;
    List<Lecture> lectures;
}
