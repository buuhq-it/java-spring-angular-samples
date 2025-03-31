package vuxe.infrastructure.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vuxe.domain.management.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
