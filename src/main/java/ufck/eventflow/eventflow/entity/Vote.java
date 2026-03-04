package ufck.eventflow.eventflow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "votes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Vote {

    @EmbeddedId
    private VoteKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @Enumerated(EnumType.STRING)
    private VoteType type;
}