package com.yorisen.ws.iteventsaggregator.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class Lecture implements Serializable {
    @Serial
    private static final long serialVersionUID = -9130848786934638900L;
    private List<Speaker> speakers;
    private String theme;
}
