package com.yorisen.ws.iteventsaggregator.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Speaker implements Serializable {
    @Serial
    private static final long serialVersionUID = -3312987451579526116L;
    private String name;
    private String description;
}
