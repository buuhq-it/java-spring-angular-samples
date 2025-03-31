package vuxe.domain.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "persistent_logins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersistentLogin {
    @Id
    @Column(length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false)
    private LocalDateTime lastUsed;
}
