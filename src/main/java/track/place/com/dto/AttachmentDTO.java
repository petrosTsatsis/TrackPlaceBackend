package track.place.com.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AttachmentDTO {
    private UUID id;
    private String filename;
    private String url;
    private LocalDateTime uploadedAt;
    private UUID uploadedById;
    private UUID taskId;
}