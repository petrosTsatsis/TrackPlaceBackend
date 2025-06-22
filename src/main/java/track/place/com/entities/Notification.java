package track.place.com.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import track.place.com.entities.enums.DeliveryMethod;
import track.place.com.entities.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Notification message is required")
    @Size(max = 1000, message = "Notification message is too long")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(updatable = false)
    private UUID relatedEntityId;

    @Column(nullable = false)
    private boolean isRead;

    @NotNull(message = "Notification must have a user")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Notification type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @NotNull(message = "Notification delivery method is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryMethod deliveryMethod;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        isRead = false;
    }
}
