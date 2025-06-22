package track.place.com.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import track.place.com.entities.enums.InvitationStatus;
import track.place.com.entities.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invitations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Token is required")
    @Column(nullable = false, unique = true)
    private String token;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime sentAt;

    @Column()
    private LocalDateTime acceptedAt;

    @NotNull(message = "Invitation status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @PrePersist
    protected void onCreate() {
        if (sentAt == null) {
            sentAt = LocalDateTime.now();
        }
        if (status == null) {
            status = InvitationStatus.PENDING;
        }
        // Validate that either workspace or project is set, but not both
        if ((workspace == null && project == null) || (workspace != null && project != null)) {
            throw new IllegalStateException("Invitation must be associated with either a workspace or a project, but not both");
        }
    }
}
