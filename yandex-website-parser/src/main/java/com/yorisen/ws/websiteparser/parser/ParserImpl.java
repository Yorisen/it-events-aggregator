package com.yorisen.ws.websiteparser.parser;

import com.yorisen.ws.iteventsaggregator.dto.Speaker;
import com.yorisen.ws.iteventsaggregator.dto.Event;
import com.yorisen.ws.iteventsaggregator.dto.Lecture;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Component
public class ParserImpl implements Parser {
    @Value("${browser.url}")
    private String url;
    @Value("${browser.agent}")
    private String agent;
    @Value("${browser.connection.timeout}")
    private Integer connectionTimeout;

    @Override
    public List<Event> parseEvents() {
        Connection connection = getConnection(url);
        List<Event> events = new ArrayList<>();

        try {
            Document eventDocument = connection.get();
            Elements eventElements = eventDocument.getElementsByClass("event-card");
            eventElements.forEach(eventElement -> {
                Event event = new Event();
                event.setName(eventElement.getElementsByClass("event-card__title").attr("title"));

                Element linkElement = eventElement.getElementsByClass("event-card__title").first();
                if (linkElement != null) {
                    String link = linkElement.absUrl("href");
                    event.setLink(link);
                }

                String dateString = eventElement.getElementsByClass("event-card__date").text();
                event.setCity(getCity(dateString));

                dateString = cleanDateString(dateString);
                LocalDate date = getLocalDate(dateString);
                event.setDate(date);

                Connection eventDetailsConnection = getConnection(event.getLink());
                try {
                    Thread.sleep((long) (Math.random() * 20000));
                    Document eventDetails = eventDetailsConnection.get();
                    parseLecture(event.getLink(), eventDetails, event);
                } catch (IOException | InterruptedException e) {
                    //do nothing
                }

                events.add(event);
            });

        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }

        return events;
    }

    private Connection getConnection(String url) {
        Connection connection = Jsoup.connect(url);
        connection.userAgent(agent);
        connection.timeout(connectionTimeout);

        return connection;
    }

    private static LocalDate getLocalDate(String sourceDate) {
        String date = sourceDate.trim();
        DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                .withLocale(new Locale("ru"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, d MMMM yyyy", new Locale("ru"));
        try {
            if (!date.toUpperCase().contains("СЕГОДНЯ")) {
                return LocalDate.parse(date + " " + LocalDate.now().getYear(), formatter);
            } else {
                return LocalDate.now();
            }
        } catch (Exception ex) {
            try {
                return LocalDate.parse(date + " " + (LocalDate.now().getYear() + 1), formatter);
            } catch (Exception ex1) {
                try {
                    return LocalDate.parse(date + " " + (LocalDate.now().getYear() - 1), formatter);
                } catch (Exception ex2) {
                    return null;
                }
            }
        }
    }

    private static String getCity(String dateString) {
        String[] dateArray = dateString.split(",");
        String city = "";
        if (dateArray.length > 2) {
            city = dateArray[2].trim();
        }

        return city;
    }

    private static String cleanDateString(String dateString) {
        String[] sourceDateArray = dateString.split(",");
        List<String> dateArray = new ArrayList<>();

        dateArray.add(sourceDateArray[0]);
        if (sourceDateArray.length > 1) {
            dateArray.add(sourceDateArray[1]);
        }

        return String.join(",", dateArray);
    }

    private static void parseLecture(String url, Document document, Event event) {
        List<Lecture> lectures = new ArrayList<>();

        if (url.contains("cloud.yandex.ru")) {
            String description = document.getElementsByClass("EventInfo__description").text();
            event.setDescription(description);

            Speaker speaker = new Speaker();
            speaker.setName(document.getElementsByClass("EventSpeaker__name").text());
            speaker.setDescription(document.getElementsByClass("EventSpeaker__description").text());

            List<Speaker> speakers = new ArrayList<>();
            speakers.add(speaker);

            Lecture lecture = new Lecture();
            lecture.setSpeakers(speakers);
            lecture.setTheme(description);

            lectures.add(lecture);
        } else if (url.contains("events.yandex.ru")) {
            Element about = document.getElementById("about");
            if (about != null) {
                String description = about.text();
                event.setDescription(description);

                Elements programItems = document.select(".lc-events-program__container .lc-events-program-item");
                programItems.forEach(item -> {
                    Lecture lecture = new Lecture();
                    lecture.setTheme(item.getElementsByClass("lc-events-program-item__talk").text());

                    List<Speaker> speakers = new ArrayList<>();
                    Elements speakersElements = item.select(".lc-events-program-item__speakers .lc-events-speaker");
                    speakersElements.forEach(speakerElement -> {
                        Speaker speaker = new Speaker();
                        speaker.setName(speakerElement.getElementsByClass("lc-events-speaker__name").text());
                        speaker.setDescription(speakerElement.getElementsByClass("lc-events-speaker__company").text());

                        speakers.add(speaker);
                    });

                    lecture.setSpeakers(speakers);
                    lectures.add(lecture);
                });
            } else {
                log.warn("No class 'about' on page {}, maybe robot check...", url);
            }
        } else if (url.contains("praktikum.yandex.ru")) {
            Element about = document.getElementById("tekst");
            if (about != null) {
                Element descriptionElement = document.getElementById("tekst");
                if (descriptionElement != null) {
                    String description = descriptionElement.text();
                    event.setDescription(description);
                }

                Elements lectureNames = document.select("section[id^=tekst-0]");

                lectureNames.forEach(lectureElement -> {
                    String lectureId = lectureElement.attr("id");
                    String speakerId = "opisanie-" + lectureId.replace("tekst-", "");
                    Element speakerElement = document.getElementById(speakerId);
                    List<Speaker> speakers = new ArrayList<>();
                    if (speakerElement != null) {
                        String speakerOrganization = speakerElement.getElementsByClass("org").text();
                        String speakerProfession = speakerElement.getElementsByClass("prof").text();
                        String speakerInfo = speakerElement.getElementsByClass("lc-styled-text__text").text();
                        String speakerName = speakerInfo
                                .replace(speakerProfession, "")
                                .replace(speakerOrganization, "")
                                .trim();
                        String speakerDescription = speakerOrganization + ", " + speakerProfession;
                        Speaker speaker = new Speaker();
                        speaker.setName(speakerName);
                        speaker.setDescription(speakerDescription);

                        speakers.add(speaker);
                    }

                    Lecture lecture = new Lecture();
                    lecture.setTheme(lectureElement.getElementsByClass("lc-styled-text__text").text());
                    lecture.setSpeakers(speakers);

                    lectures.add(lecture);
                });
            } else {
                log.warn("No id 'tekst' on page {}, maybe robot check...", url);
            }
        } else if (url.contains("yandex.ru/promo")) {
            log.info("Сайты с адресами 'yandex.ru/promo' верстаются по-разному, нет общего шаблона или пока я его не выявил");
        }

        event.setLectures(lectures);
    }

}
