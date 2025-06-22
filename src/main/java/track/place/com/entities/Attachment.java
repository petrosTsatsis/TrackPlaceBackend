package track.place.com.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Filename is required")
    @Size(min = 1, max = 255, message = "Filename must be between 1 and 255 characters")
    @Column(nullable = false)
    private String filename;

    @NotBlank(message = "URL is required")
    @Size(max = 2000, message = "URL must be less than 2000 characters")
    @Column(nullable = false)
    private String url;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    @NotNull(message = "Uploader is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uploaded_by_id", nullable = false)
    private User uploadedBy;

    @NotNull(message = "Attachment must belong to a task")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @PrePersist
    protected void onCreate() {
        if (uploadedAt == null) {
            uploadedAt = LocalDateTime.now();
        }
    }
}
