package track.place.com.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class WorkspaceDTO {
    private UUID id;
    private String name;
    private String description;
    private String settings;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID ownerId;
}