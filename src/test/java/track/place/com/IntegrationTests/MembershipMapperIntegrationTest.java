package track.place.com.IntegrationTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import track.place.com.dto.MembershipDTO;
import track.place.com.entities.Membership;
import track.place.com.entities.Project;
import track.place.com.entities.User;
import track.place.com.entities.Workspace;
import track.place.com.entities.enums.Role;
import track.place.com.util.mapper.MembershipMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class MembershipMapperIntegrationTest {

    @Autowired
    private MembershipMapper membershipMapper;

    @Test
    public void testMembershipToDTOAndBack() {

        Membership membership = new Membership();
        membership.setId(UUID.randomUUID());
        membership.setRole(Role.ADMIN);
        membership.setCreatedAt(LocalDateTime.now());

        User user = new User();
        user.setId(UUID.randomUUID());
        membership.setUser(user);

        Workspace workspace = new Workspace();
        workspace.setId(UUID.randomUUID());
        membership.setWorkspace(workspace);

        Project project = new Project();
        project.setId(UUID.randomUUID());
        membership.setProject(project);

        // Entity to DTO
        MembershipDTO dto = membershipMapper.toDTO(membership);
        assertNotNull(dto);
        assertEquals(membership.getId(), dto.getId());
        assertEquals(membership.getRole(), dto.getRole());
        assertEquals(membership.getCreatedAt(), dto.getCreatedAt());
        assertEquals(user.getId(), dto.getUserId());
        assertEquals(workspace.getId(), dto.getWorkspaceId());
        assertNotNull(dto.getProjectId());

        // DTO to Entity
        Membership mappedEntity = membershipMapper.toEntity(dto);
        assertNotNull(mappedEntity);
        assertEquals(dto.getId(), mappedEntity.getId());
        assertEquals(dto.getRole(), mappedEntity.getRole());
        assertEquals(dto.getCreatedAt(), mappedEntity.getCreatedAt());
        assertNotNull(mappedEntity.getUser());
        assertEquals(dto.getUserId(), mappedEntity.getUser().getId());
        assertNotNull(mappedEntity.getWorkspace());
        assertEquals(dto.getWorkspaceId(), mappedEntity.getWorkspace().getId());
        assertNotNull(mappedEntity.getProject());
    }
}
