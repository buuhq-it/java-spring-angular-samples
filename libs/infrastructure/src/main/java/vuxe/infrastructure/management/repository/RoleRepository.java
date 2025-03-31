package vuxe.infrastructure.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vuxe.domain.management.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
