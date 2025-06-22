package track.place.com.dto;

import lombok.Data;
import track.place.com.entities.enums.Role;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MembershipDTO {
    private UUID id;
    private UUID userId;
    private UUID workspaceId;
    private UUID projectId;
    private Role role;
    private LocalDateTime createdAt;
}