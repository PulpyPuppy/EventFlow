package ufck.eventflow.eventflow.repository;

// Oh, Holy AI, how much time I would waste without you...
import ufck.eventflow.eventflow.entity.Event;
import ufck.eventflow.eventflow.entity.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStatusOrderByVotesCountDesc(EventStatus status); // For feed
    // C'mon, it's not a function, it's an Essay!.. I would call it get_underrated_active
    List<Event> findByStatusAndVotesCountLessThanEqual(EventStatus status, int threshold); // Candidates to ban
    List<Event> findByAuthorId(java.util.UUID authorId); // Guess what
}