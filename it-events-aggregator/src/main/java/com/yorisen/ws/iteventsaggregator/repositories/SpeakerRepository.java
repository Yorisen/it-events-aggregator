package com.yorisen.ws.iteventsaggregator.repositories;

import com.yorisen.ws.iteventsaggregator.models.Speaker;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface SpeakerRepository  extends JpaRepository<Speaker, BigDecimal> {

    @EntityGraph(attributePaths = {"lectures"})
    Optional<Speaker> findByName(String name);
}
