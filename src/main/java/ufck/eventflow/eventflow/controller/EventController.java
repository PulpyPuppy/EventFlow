package ufck.eventflow.eventflow.controller;

import ufck.eventflow.eventflow.dto.*;
import ufck.eventflow.eventflow.entity.*;
import ufck.eventflow.eventflow.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.stream.Collectors;

// I swear, it's easier to manage 10k line of C code,
// rather than all this bunch of Java files.
// I don't get how they relate to each other, pls somebody save me
// No, really, I don't get the concept of "Chill, the DI somehow,
// somewhere connect something with another something", do you?
// C'mon, it's a bullshit
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final VoteService voteService;
    private final UserService userService;

    @GetMapping("/feed")
    public List<EventResponseDTO> getFeed() {
        return eventService.getActiveFeed().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventRequestDTO dto) {
        User currentUser = getCurrentUser();

        Event event = new Event();
        event.setTitle(dto.title());
        event.setDescription(dto.description());

        Event saved = eventService.createEventDraft(event, currentUser);
        return ResponseEntity.ok(convertToDTO(saved));
    }

    // Programming is getting something like plankton work, you know
    @PostMapping("/{id}/vote")
    public ResponseEntity<Void> vote(@PathVariable Long id, @RequestBody VoteRequestDTO voteDto) {
        User currentUser = getCurrentUser();
        voteService.castVote(currentUser, id, voteDto.type());
        return ResponseEntity.ok().build();
    }

    private EventResponseDTO convertToDTO(Event event) {
        // You see mapping for the first time?
        return new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAuthor().getUsername(),
                event.getVotesCount(),
                event.getStatus(),
                event.getCreatedAt()
        );
    }

    private User getCurrentUser() {
        // Why so many files? There is no logic here, it's all about abstractions
        // I mean, you guys like playing Sims with all this Spring stuff, not real engineering, sorry
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.getByUsername(username);
    }
}
