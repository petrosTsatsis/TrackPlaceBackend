package track.place.com.UnitTests.MapperTests;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
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

public class MembershipMapperTest {
    private MembershipMapper membershipMapper = Mappers.getMapper(MembershipMapper.class);

    @Test
    public void testMapMembershipToMembershipDTO() {
        UUID membershipId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Membership membership = new Membership();
        membership.setId(membershipId);
        membership.setRole(Role.ADMIN);
        membership.setCreatedAt(now);

        User user = new User();
        user.setId(UUID.randomUUID());
        membership.setUser(user);

        Workspace workspace = new Workspace();
        workspace.setId(UUID.randomUUID());
        membership.setWorkspace(workspace);

        Project project = new Project();
        project.setId(UUID.randomUUID());
        membership.setProject(project);

        MembershipDTO dto = membershipMapper.toDTO(membership);

        assertNotNull(dto);
        assertEquals(membershipId, dto.getId());
        assertEquals(Role.ADMIN, dto.getRole());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(user.getId(), dto.getUserId());
        assertEquals(workspace.getId(), dto.getWorkspaceId());
        assertEquals(project.getId(), dto.getProjectId());
    }

    @Test
    public void testMapMembershipDTOToMembership() {
        UUID membershipId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        MembershipDTO dto = new MembershipDTO();
        dto.setId(membershipId);
        dto.setRole(Role.MEMBER);
        dto.setCreatedAt(now);
        dto.setUserId(UUID.randomUUID());
        dto.setWorkspaceId(UUID.randomUUID());
        dto.setProjectId(UUID.randomUUID());

        Membership membership = membershipMapper.toEntity(dto);

        assertNotNull(membership);
        assertEquals(membershipId, membership.getId());
        assertEquals(Role.MEMBER, membership.getRole());
        assertEquals(now, membership.getCreatedAt());
        assertNotNull(membership.getUser());
        assertNotNull(membership.getWorkspace());
        assertNotNull(membership.getProject());
        assertEquals(dto.getUserId(), membership.getUser().getId());
    }
}

