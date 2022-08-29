package mk.ukim.finki.wp.jan2021.events.service.impl;

import mk.ukim.finki.wp.jan2021.events.model.Event;
import mk.ukim.finki.wp.jan2021.events.model.EventLocation;
import mk.ukim.finki.wp.jan2021.events.model.EventType;
import mk.ukim.finki.wp.jan2021.events.model.exceptions.InvalidEventIdException;
import mk.ukim.finki.wp.jan2021.events.model.exceptions.InvalidEventLocationIdException;
import mk.ukim.finki.wp.jan2021.events.repository.EventLocationRepository;
import mk.ukim.finki.wp.jan2021.events.repository.EventRepository;
import mk.ukim.finki.wp.jan2021.events.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventLocationRepository eventLocationRepository;

    public EventServiceImpl(EventRepository eventRepository, EventLocationRepository eventLocationRepository) {
        this.eventRepository = eventRepository;
        this.eventLocationRepository = eventLocationRepository;
    }

    @Override
    public List<Event> listAllEvents() {
        return this.eventRepository.findAll();
    }

    @Override
    public Event findById(Long id) {
        return this.eventRepository.findById(id).orElseThrow(InvalidEventIdException::new);
    }

    @Override
    public Event create(String name, String description, Double price, EventType type, Long location) {
        EventLocation el = this.eventLocationRepository.findById(location).orElseThrow(InvalidEventLocationIdException::new);
        Event e = new Event(name, description, price, type, el);
        this.eventRepository.save(e);
        return e;
    }

    @Override
    public Event update(Long id, String name, String description, Double price, EventType type, Long location) {
        EventLocation el = this.eventLocationRepository.findById(location).orElseThrow(InvalidEventLocationIdException::new);
        Event e = this.eventRepository.findById(id).orElseThrow(InvalidEventIdException::new);
        e.setName(name);
        e.setDescription(description);
        e.setPrice(price);
        e.setType(type);
        e.setLocation(el);
        this.eventRepository.save(e);
        return e;
    }

    @Override
    public Event delete(Long id) {
        Event e = this.eventRepository.findById(id).orElseThrow(InvalidEventIdException::new);
        this.eventRepository.deleteById(id);
        return e;
    }

    @Override
    public Event like(Long id) {
        Event e = this.eventRepository.findById(id).orElseThrow(InvalidEventIdException::new);
        e.setLikes(e.getLikes()+1);
        this.eventRepository.save(e);
        return e;
    }

    @Override
    public List<Event> listEventsWithPriceLessThanAndType(Double price, EventType type) {
        return this.eventRepository.findAll().stream().filter(event -> {
            return event.getPrice() < price && event.getType() == type;
        }).collect(Collectors.toList());

    }
}
