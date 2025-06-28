package track.place.com.UnitTests.MapperTests;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import track.place.com.dto.UserDTO;
import track.place.com.entities.User;
import track.place.com.entities.enums.Role;
import track.place.com.util.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserMapperTest {

    // Direct access to the mapper implementation
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testMapUserToUserDTO() {
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setRole(Role.ADMIN);
        user.setNotificationPreferences("{\"emailNotifications\": true}");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        // Act
        UserDTO userDTO = userMapper.toDTO(user);

        // Assert
        assertNotNull(userDTO);
        assertEquals(userId, userDTO.getId());
        assertEquals("test@example.com", userDTO.getEmail());
        assertEquals("Test User", userDTO.getName());
        assertEquals("{\"emailNotifications\": true}", userDTO.getNotificationPreferences());
        assertEquals(now, userDTO.getCreatedAt());
        assertEquals(now, userDTO.getUpdatedAt());
        assertEquals(Role.ADMIN, userDTO.getRole());
    }

    @Test
    public void testMapUserDTOToUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setEmail("test@example.com");
        userDTO.setName("Test User");
        userDTO.setRole(Role.MEMBER);
        userDTO.setNotificationPreferences("{\"emailNotifications\": false}");
        userDTO.setCreatedAt(now);
        userDTO.setUpdatedAt(now);

        // Act
        User user = userMapper.toEntity(userDTO);

        // Assert
        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Test User", user.getName());
        assertEquals("{\"emailNotifications\": false}", user.getNotificationPreferences());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
        assertEquals(Role.MEMBER, user.getRole());
    }

}

