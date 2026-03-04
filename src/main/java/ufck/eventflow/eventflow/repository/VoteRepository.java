package ufck.eventflow.eventflow.repository;

import ufck.eventflow.eventflow.entity.Vote;
import ufck.eventflow.eventflow.entity.VoteKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

// Do you read my comments? All of them? I hope you don't
public interface VoteRepository extends JpaRepository<Vote, VoteKey> {
    Optional<Vote> findByIdUserIdAndIdEventId(UUID userId, Long eventId); // For preventing double-votes
    long countByUserId(UUID userId); // For reputation
}