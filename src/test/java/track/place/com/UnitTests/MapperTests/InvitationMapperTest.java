package track.place.com.UnitTests.MapperTests;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import track.place.com.dto.InvitationDTO;
import track.place.com.entities.Invitation;
import track.place.com.entities.Workspace;
import track.place.com.entities.enums.InvitationStatus;
import track.place.com.entities.enums.Role;
import track.place.com.util.mapper.InvitationMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InvitationMapperTest {
    private InvitationMapper invitationMapper = Mappers.getMapper(InvitationMapper.class);

    @Test
    public void testMapInvitationToInvitationDTO() {
        UUID invitationId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Invitation invitation = new Invitation();
        invitation.setId(invitationId);
        invitation.setEmail("test@example.com");
        invitation.setToken("token123");
        invitation.setSentAt(now);
        invitation.setAcceptedAt(now.plusDays(1));
        invitation.setStatus(InvitationStatus.PENDING);
        invitation.setRole(Role.MEMBER);

        Workspace workspace = new Workspace();
        workspace.setId(UUID.randomUUID());
        invitation.setWorkspace(workspace);

        InvitationDTO dto = invitationMapper.toDTO(invitation);

        assertNotNull(dto);
        assertEquals(invitationId, dto.getId());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals(now, dto.getSentAt());
        assertEquals(now.plusDays(1), dto.getAcceptedAt());
        assertEquals(InvitationStatus.PENDING, dto.getStatus());
        assertEquals(Role.MEMBER, dto.getRole());
        assertEquals(workspace.getId(), dto.getWorkspaceId());
        // projectId will be null
    }

    @Test
    public void testMapInvitationDTOToInvitation() {
        UUID invitationId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        InvitationDTO dto = new InvitationDTO();
        dto.setId(invitationId);
        dto.setEmail("dto@example.com");
        dto.setSentAt(now);
        dto.setAcceptedAt(now.plusDays(2));
        dto.setStatus(InvitationStatus.ACCEPTED);
        dto.setRole(Role.ADMIN);
        dto.setWorkspaceId(UUID.randomUUID());
        dto.setProjectId(null);

        Invitation invitation = invitationMapper.toEntity(dto);

        assertNotNull(invitation);
        assertEquals(invitationId, invitation.getId());
        assertEquals("dto@example.com", invitation.getEmail());
        assertEquals(now, invitation.getSentAt());
        assertEquals(now.plusDays(2), invitation.getAcceptedAt());
        assertEquals(InvitationStatus.ACCEPTED, invitation.getStatus());
        assertEquals(Role.ADMIN, invitation.getRole());
        assertNotNull(invitation.getWorkspace());
        // workspace and project will be null unless you handle mapping by id
    }
}

