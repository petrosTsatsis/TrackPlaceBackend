package track.place.com.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BoardListDTO {
    private UUID id;
    private String name;
    private Integer position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID boardId;
}