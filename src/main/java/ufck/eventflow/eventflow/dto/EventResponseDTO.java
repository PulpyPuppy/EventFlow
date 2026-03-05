package ufck.eventflow.eventflow.dto;

import ufck.eventflow.eventflow.entity.EventStatus;

import java.time.LocalDateTime;

public record EventResponseDTO(
        Long id,
        String title,
        String description,
        String authorName,
        int votesCount,
        EventStatus status,
        LocalDateTime createdAt
) {
}
