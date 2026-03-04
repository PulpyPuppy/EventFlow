package ufck.eventflow.eventflow.repository;

import ufck.eventflow.eventflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID; // Не, ну надо ж как-то финтануть, правильно?

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}