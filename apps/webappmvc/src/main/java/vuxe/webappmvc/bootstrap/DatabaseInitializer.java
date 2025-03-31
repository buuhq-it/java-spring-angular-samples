package vuxe.webappmvc.bootstrap;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vuxe.domain.management.entity.Role;
import vuxe.domain.management.entity.User;
import vuxe.infrastructure.management.repository.RoleRepository;
import vuxe.infrastructure.management.repository.UserRepository;

import java.util.Set;

@Service
public class DatabaseInitializer {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(UserRepository userRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // Check and create roles if they don't exist
        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("USER").build()));

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").build()));

        Role guestRole = roleRepository.findByName("GUEST")
                .orElseGet(() -> roleRepository.save(Role.builder().name("GUEST").build()));

        // Create users if not exists
        if (userRepository.findByUsername("user").isEmpty()) {
            userRepository.save(User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("password"))
                    .roles(Set.of(userRole))
                    .build());
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("password"))
                    .roles(Set.of(userRole, adminRole))
                    .build());
        }

        if (userRepository.findByUsername("kermit").isEmpty()) {
            userRepository.save(User.builder()
                    .username("kermit")
                    .password(passwordEncoder.encode("kermit"))
                    .roles(Set.of(userRole, adminRole))
                    .build());
        }

        if (userRepository.findByUsername("gonzo").isEmpty()) {
            userRepository.save(User.builder()
                    .username("gonzo")
                    .password(passwordEncoder.encode("gonzo"))
                    .roles(Set.of(userRole, adminRole))
                    .build());
        }

        if (userRepository.findByUsername("fozzie").isEmpty()) {
            userRepository.save(User.builder()
                    .username("fozzie")
                    .password(passwordEncoder.encode("fozzie"))
                    .roles(Set.of(userRole))
                    .build());
        }
    }
}
