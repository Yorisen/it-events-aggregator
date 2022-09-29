package com.yorisen.ws.iteventsaggregator.converters;

import com.yorisen.ws.iteventsaggregator.models.Lecture;
import com.yorisen.ws.iteventsaggregator.models.Speaker;
import com.yorisen.ws.iteventsaggregator.services.SpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SpeakerConverter {
    private final SpeakerService speakerService;

    List<Speaker> convert (List<com.yorisen.ws.iteventsaggregator.dto.Speaker> dtoSpeakers, List<Lecture> lectures) {
        List<Speaker> speakers = new ArrayList<>();
        if (dtoSpeakers != null) {
            for (com.yorisen.ws.iteventsaggregator.dto.Speaker dtoSpeaker: dtoSpeakers) {
                speakers.add(convert(dtoSpeaker, lectures));
            }
        }

        return speakers;
    }

    private Speaker convert(com.yorisen.ws.iteventsaggregator.dto.Speaker dtoSpeaker, List<Lecture> lectures) {
        Optional<Speaker> speakerOptional = speakerService.findByName(dtoSpeaker.getName());
        Speaker speaker;
        if (speakerOptional.isPresent()) {
            speaker = speakerOptional.get();
            String description = speaker.getDescription();
            if (!description.contains(dtoSpeaker.getDescription())) {
                description += dtoSpeaker.getDescription();
                speaker.setDescription(description);
            }
        } else {
            speaker = new Speaker();
            speaker.setName(dtoSpeaker.getName());
            speaker.setDescription(dtoSpeaker.getDescription());
            speaker.setLectures(lectures);
        }

        return speaker;
    }


}
