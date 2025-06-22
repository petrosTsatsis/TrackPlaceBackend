package track.place.com.dto;

import lombok.Data;
import track.place.com.entities.enums.BoardType;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class BoardDTO {
    private UUID id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BoardType type;
    private UUID projectId;
    private Set<UUID> boardListIds;
}