package com.yorisen.ws.iteventsaggregator.services;

import com.yorisen.ws.iteventsaggregator.models.Lecture;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LectureService {
    List<Lecture> findAllByEventId(BigDecimal id);

    Optional<Lecture> findById(BigDecimal id);
    Lecture save(Lecture lecture);
}
