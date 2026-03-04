package ufck.eventflow.eventflow.service;

import ufck.eventflow.eventflow.entity.Event;
import ufck.eventflow.eventflow.entity.EventStatus;
import ufck.eventflow.eventflow.entity.User;
import ufck.eventflow.eventflow.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final PaymentMockService paymentService;

    @Transactional
    public Event createEventDraft(Event event, User author) {
        event.setAuthor(author);
        event.setStatus(EventStatus.PENDING_PAYMENT);
        return eventRepository.save(event);
    }

    // What the hell am I doing..?
    @Transactional
    public void processEventActivation(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Charge the user
        boolean success = paymentService.processPayment(event.getAuthor().getId().toString(), 5.00);

        if (success) { // Hurray
            event.setStatus(EventStatus.ACTIVE);
            eventRepository.save(event);
        }
    }

    // Bruh, why are these instructions so long-named?
    public List<Event> getActiveFeed() {
        return eventRepository.findByStatusOrderByVotesCountDesc(EventStatus.ACTIVE);
    }
}
