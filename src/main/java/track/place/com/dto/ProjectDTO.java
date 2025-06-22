package track.place.com.dto;

import lombok.Data;
import track.place.com.entities.enums.Label;
import track.place.com.entities.enums.ProjectVisibility;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class ProjectDTO {
    private UUID id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectVisibility visibility;
    private UUID projectLeadId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID workspaceId;
    private Set<Label> projectLabels;
}