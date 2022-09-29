package com.yorisen.ws.iteventsaggregator.repositories;

import com.yorisen.ws.iteventsaggregator.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface LectureRepository extends JpaRepository<Lecture, BigDecimal> {

}
