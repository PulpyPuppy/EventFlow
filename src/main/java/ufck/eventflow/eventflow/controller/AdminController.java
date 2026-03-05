package ufck.eventflow.eventflow.controller;

import ufck.eventflow.eventflow.entity.Event;
import ufck.eventflow.eventflow.entity.EventStatus;
import ufck.eventflow.eventflow.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final EventRepository eventRepository;

    @GetMapping("/banned")
    public List<Event> getBannedEvents() {
        return eventRepository.findByStatusAndVotesCountLessThanEqual(EventStatus.SOFT_BANNED, 100);
    }
}