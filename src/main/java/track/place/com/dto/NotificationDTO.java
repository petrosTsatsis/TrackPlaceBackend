package track.place.com.dto;

import lombok.Data;
import track.place.com.entities.enums.DeliveryMethod;
import track.place.com.entities.enums.NotificationType;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class NotificationDTO {
    private UUID id;
    private String message;
    private UUID relatedEntityId;
    private boolean isRead;
    private UUID userId;
    private NotificationType type;
    private DeliveryMethod deliveryMethod;
    private LocalDateTime createdAt;
}