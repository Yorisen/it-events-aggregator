package com.yorisen.ws.iteventsaggregator.services;

import com.yorisen.ws.iteventsaggregator.models.Speaker;
import com.yorisen.ws.iteventsaggregator.repositories.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpeakerServiceImpl implements SpeakerService {
    private final SpeakerRepository speakerRepository;

    @Override
    public List<Speaker> findAll() {
        return speakerRepository.findAll();
    }

    @Override
    public Optional<Speaker> findById(BigDecimal id) {
        return speakerRepository.findById(id);
    }

    @Override
    public Optional<Speaker> findByName(String name) {
        return speakerRepository.findByName(name);
    }

    @Override
    public Speaker save(Speaker speaker) {
        return speakerRepository.save(speaker);
    }
}
