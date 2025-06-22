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
import track.place.com.entities.enums.TaskPriority;
import track.place.com.entities.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Task title is required")
    @Size(min = 1, max = 200, message = "Task title must be between 1 and 200 characters")
    @Column(nullable = false)
    private String title;

    @Size(max = 5000, message = "Task description is too long")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column()
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column()
    private TaskPriority priority;

    @NotNull(message = "Task status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @NotNull(message = "Task must belong to a project")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "board_list_id")
    private BoardList boardList;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "task")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "task")
    private Set<Attachment> attachments = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Set<Label> taskLabels = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (status == null) {
            status = TaskStatus.TO_DO;
        }
        if (priority == null) {
            priority = TaskPriority.MEDIUM;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
