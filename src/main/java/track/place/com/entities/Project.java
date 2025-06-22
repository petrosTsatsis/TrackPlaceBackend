package track.place.com.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import track.place.com.entities.enums.Label;
import track.place.com.entities.enums.ProjectVisibility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Project name is required")
    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @Size(max = 2000, message = "Description is too long")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column()
    private LocalDate startDate;

    @Column()
    private LocalDate endDate;

    @NotNull(message = "Project visibility is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectVisibility visibility;

    @ManyToOne
    @JoinColumn(name = "project_lead_id")
    private User projectLead;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @NotNull(message = "Project must belong to a workspace")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @OneToMany(mappedBy = "project")
    private Set<Board> boards = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Label> projectLabels = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Invitation> invitations = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<GitHubIntegration> githubIntegrations = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (visibility == null) {
            visibility = ProjectVisibility.PRIVATE;
        }
        // Validate date consistency
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalStateException("End date cannot be before start date");
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
