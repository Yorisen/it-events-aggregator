package com.yorisen.ws.iteventsaggregator.services;

import com.yorisen.ws.iteventsaggregator.models.Event;
import com.yorisen.ws.iteventsaggregator.models.Lecture;
import com.yorisen.ws.iteventsaggregator.repositories.EventRepository;
import com.yorisen.ws.iteventsaggregator.repositories.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {
    private final LectureRepository lectureRepository;
    private final EventRepository eventRepository;

    @Override
    public List<Lecture> findAllByEventId(BigDecimal id) {
        List<Lecture> eventLectures = new ArrayList<>();
        Optional<Event> foundEventOptional = eventRepository.findById(id);
        if (foundEventOptional.isPresent()) {
            eventLectures = foundEventOptional.get().getLectures();
        }

        return eventLectures;
    }

    @Override
    public Optional<Lecture> findById(BigDecimal id) {
        return lectureRepository.findById(id);
    }

    @Override
    public Lecture save(Lecture lecture) {
        return lectureRepository.save(lecture);
    }
}
