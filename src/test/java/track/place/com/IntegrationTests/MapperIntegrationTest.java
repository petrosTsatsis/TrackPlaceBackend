package track.place.com.IntegrationTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import track.place.com.dto.UserDTO;
import track.place.com.entities.User;
import track.place.com.entities.enums.Role;
import track.place.com.util.mapper.UserMapper;


import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class MapperIntegrationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapperIsInjectedCorrectly() {
        // Verify the Spring-managed instance is properly injected
        assertNotNull(userMapper);

        // Create a user entity
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("integration@test.com");
        user.setName("Integration Test");
        user.setRole(Role.MEMBER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Convert to DTO
        UserDTO userDTO = userMapper.toDTO(user);

        // Verify conversion
        assertNotNull(userDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getRole(), userDTO.getRole());
    }
}

