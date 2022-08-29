package com.yorisen.ws.iteventsaggregator.services;

import com.yorisen.ws.iteventsaggregator.models.Speaker;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SpeakerService {
    List<Speaker> findAll();
    Optional<Speaker> findById(BigDecimal id);
    Optional<Speaker> findByName(String name);
    Speaker save(Speaker speaker);
}
