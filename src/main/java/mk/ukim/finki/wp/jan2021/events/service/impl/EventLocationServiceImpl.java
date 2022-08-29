package mk.ukim.finki.wp.jan2021.events.service.impl;

import mk.ukim.finki.wp.jan2021.events.model.EventLocation;
import mk.ukim.finki.wp.jan2021.events.model.exceptions.InvalidEventLocationIdException;
import mk.ukim.finki.wp.jan2021.events.repository.EventLocationRepository;
import mk.ukim.finki.wp.jan2021.events.service.EventLocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventLocationServiceImpl implements EventLocationService {

    private final EventLocationRepository eventLocationRepository;

    public EventLocationServiceImpl(EventLocationRepository eventLocationRepository) {
        this.eventLocationRepository = eventLocationRepository;
    }

    @Override
    public EventLocation findById(Long id) {
        return this.eventLocationRepository.findById(id).orElseThrow(InvalidEventLocationIdException::new);
    }

    @Override
    public List<EventLocation> listAll() {
        return this.eventLocationRepository.findAll();
    }

    @Override
    public EventLocation create(String name) {
        EventLocation el = new EventLocation(name);
        this.eventLocationRepository.save(el);
        return el;
    }
}
