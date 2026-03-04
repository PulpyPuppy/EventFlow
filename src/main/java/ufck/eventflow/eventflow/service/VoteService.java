package ufck.eventflow.eventflow.service;

// Let's add some dependencies, mmm...
import ufck.eventflow.eventflow.entity.*;
import ufck.eventflow.eventflow.repository.EventRepository;
import ufck.eventflow.eventflow.repository.VoteRepository;
import ufck.eventflow.eventflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// I ain't got no Idea how Spring does all this magic stuff tbh...
@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    // Process the vote: up or down
    @Transactional
    public void castVote(User user, Long eventId, VoteType type) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        VoteKey key = new VoteKey(user.getId(), eventId);
        Optional<Vote> existingVote = voteRepository.findById(key);

        // If it's revoting
        if (existingVote.isPresent()) {
            Vote vote = existingVote.get();
            if (vote.getType() != type) {
                int change = (type == VoteType.UPVOTE) ? 2 : -2; // You know why, right..? Right..?
                event.setVotesCount(event.getVotesCount() + change);
                eventRepository.save(event);

                // Dear Neovim... I cannot express this pain I've experienced in IntelliJ Idea...
                vote.setType(type);
                voteRepository.save(vote);
            }
        }
        // If it's... Guess what? Yes, new vote
        else {
            Vote newVote = new Vote(key, user, event, type);
            int change = (type == VoteType.UPVOTE) ? 1 : -1;
            event.setVotesCount(event.getVotesCount() + change);

            user.setReputation(user.getReputation() + 1);

            voteRepository.save(newVote);
            eventRepository.save(event);
            userRepository.save(user);
        }
    }
}
