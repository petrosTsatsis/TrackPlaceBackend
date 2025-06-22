package track.place.com.dto;

import lombok.Data;
import track.place.com.entities.enums.Role;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String email;
    private String name;
    private String notificationPreferences;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Role role;
}