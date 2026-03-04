package ufck.eventflow.eventflow.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class VoteKey implements Serializable {
    private UUID userId;
    private Long eventId;
}