package com.yorisen.ws.iteventsaggregator.converters;

import com.yorisen.ws.iteventsaggregator.models.Event;
import com.yorisen.ws.iteventsaggregator.models.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureConverter {
    private final SpeakerConverter speakerConverter;
    List<Lecture> convert(List<com.yorisen.ws.iteventsaggregator.dto.Lecture> dtoLectures, Event event) {
        List<Lecture> lectures = new ArrayList<>();
        if (dtoLectures != null) {
            for (com.yorisen.ws.iteventsaggregator.dto.Lecture dtoLecture: dtoLectures) {
                lectures.add(convert(dtoLecture, event, lectures));
            }
        }

        return lectures;
    }

    private Lecture convert(com.yorisen.ws.iteventsaggregator.dto.Lecture dtoLecture, Event event, List<Lecture> lectures) {
        Lecture lecture = new Lecture();
        lecture.setEvent(event);
        lecture.setTheme(dtoLecture.getTheme());
        lecture.setSpeakers(speakerConverter.convert(dtoLecture.getSpeakers(), lectures));

        return lecture;
    }
}
