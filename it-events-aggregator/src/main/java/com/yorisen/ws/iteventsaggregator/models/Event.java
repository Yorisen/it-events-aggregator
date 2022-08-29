package com.yorisen.ws.iteventsaggregator.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "city")
    private String city;
    @Column(name = "link")
    private String link;

    @JsonManagedReference
    @OneToMany(mappedBy = "event", targetEntity = Lecture.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    List<Lecture> lectures;
}
