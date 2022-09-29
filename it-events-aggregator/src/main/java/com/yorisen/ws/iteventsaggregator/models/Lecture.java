package com.yorisen.ws.iteventsaggregator.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "lectures")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;
    @Column(name = "theme")
    private String theme;

    @JsonBackReference
    @ManyToOne(targetEntity = Event.class, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "lectures_speakers",
                joinColumns = @JoinColumn(name = "lecture_id"),
                inverseJoinColumns = @JoinColumn(name = "speaker_id"))
    private List<Speaker> speakers;
}
