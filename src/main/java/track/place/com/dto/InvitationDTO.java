package track.place.com.dto;

import lombok.Data;
import track.place.com.entities.enums.InvitationStatus;
import track.place.com.entities.enums.Role;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class InvitationDTO {
    private UUID id;
    private String email;
    private LocalDateTime sentAt;
    private LocalDateTime acceptedAt;
    private InvitationStatus status;
    private UUID workspaceId;
    private UUID projectId;
    private Role role;
}