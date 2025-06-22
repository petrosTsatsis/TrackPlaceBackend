package track.place.com.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "board_lists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Board list name is required")
    @Size(min = 1, max = 100, message = "Board list name must be between 1 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Position is required")
    @Min(value = 0, message = "Position must be a positive number")
    @Column(nullable = false)
    private Integer position;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "boardList")
    private Set<Task> tasks = new HashSet<>();

    @NotNull(message = "Board list must belong to a board")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (position == null) {
            position = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
