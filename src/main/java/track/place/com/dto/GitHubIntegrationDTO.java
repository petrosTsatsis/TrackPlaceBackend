package track.place.com.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GitHubIntegrationDTO {
    private UUID id;
    private String repoName;
    private LocalDateTime lastSyncAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID projectId;
}