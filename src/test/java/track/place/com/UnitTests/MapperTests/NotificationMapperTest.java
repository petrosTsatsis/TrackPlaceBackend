package track.place.com.UnitTests.MapperTests;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import track.place.com.dto.NotificationDTO;
import track.place.com.entities.Notification;
import track.place.com.entities.User;
import track.place.com.entities.enums.DeliveryMethod;
import track.place.com.entities.enums.NotificationType;
import track.place.com.util.mapper.NotificationMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationMapperTest {
    private NotificationMapper notificationMapper = Mappers.getMapper(NotificationMapper.class);

    @Test
    public void testMapNotificationToNotificationDTO() {
        UUID notificationId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setMessage("Test notification");
        notification.setRelatedEntityId(UUID.randomUUID());
        notification.setRead(true);
        notification.setType(NotificationType.TASK_ASSIGNED);
        notification.setDeliveryMethod(DeliveryMethod.EMAIL);
        notification.setCreatedAt(now);

        User user = new User();
        user.setId(UUID.randomUUID());
        notification.setUser(user);

        NotificationDTO dto = notificationMapper.toDTO(notification);

        assertNotNull(dto);
        assertEquals(notificationId, dto.getId());
        assertEquals("Test notification", dto.getMessage());
        assertEquals(notification.getRelatedEntityId(), dto.getRelatedEntityId());
        assertTrue(dto.isRead());
        assertEquals(NotificationType.TASK_ASSIGNED, dto.getType());
        assertEquals(DeliveryMethod.EMAIL, dto.getDeliveryMethod());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(user.getId(), dto.getUserId());
    }

    @Test
    public void testMapNotificationDTOToNotification() {
        UUID notificationId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        NotificationDTO dto = new NotificationDTO();
        dto.setId(notificationId);
        dto.setMessage("DTO notification");
        dto.setRelatedEntityId(UUID.randomUUID());
        dto.setRead(false);
        dto.setType(NotificationType.COMMENT_ADDED);
        dto.setDeliveryMethod(DeliveryMethod.IN_APP);
        dto.setCreatedAt(now);
        dto.setUserId(UUID.randomUUID());

        Notification notification = notificationMapper.toEntity(dto);

        assertNotNull(notification);
        assertEquals(notificationId, notification.getId());
        assertEquals("DTO notification", notification.getMessage());
        assertEquals(dto.getRelatedEntityId(), notification.getRelatedEntityId());
        assertFalse(notification.isRead());
        assertEquals(NotificationType.COMMENT_ADDED, notification.getType());
        assertEquals(DeliveryMethod.IN_APP, notification.getDeliveryMethod());
        assertEquals(now, notification.getCreatedAt());
        assertNotNull(notification.getUser());
        assertEquals(dto.getUserId(), notification.getUser().getId());
    }
}

