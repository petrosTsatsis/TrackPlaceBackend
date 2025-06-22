package track.place.com.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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
    private Set<UUID> projectIds = new HashSet<>();
    private Set<UUID> invitationIds = new HashSet<>();
}