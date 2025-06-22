package track.place.com.dto;

import lombok.Data;
import track.place.com.entities.enums.Label;
import track.place.com.entities.enums.TaskPriority;
import track.place.com.entities.enums.TaskStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class TaskDTO {
    private UUID id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskPriority priority;
    private TaskStatus status;
    private UUID projectId;
    private UUID boardListId;
    private UUID assigneeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Label> taskLabels;
}