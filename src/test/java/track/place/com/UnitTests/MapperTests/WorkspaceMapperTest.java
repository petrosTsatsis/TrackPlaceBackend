package track.place.com.UnitTests.MapperTests;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import track.place.com.dto.WorkspaceDTO;
import track.place.com.entities.Invitation;
import track.place.com.entities.Project;
import track.place.com.entities.User;
import track.place.com.entities.Workspace;
import track.place.com.repositories.InvitationRepository;
import track.place.com.repositories.ProjectRepository;
import track.place.com.util.mapper.WorkspaceMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class WorkspaceMapperTest {
    private WorkspaceMapper workspaceMapper = Mappers.getMapper(WorkspaceMapper.class);

    @Test
    public void testMapWorkspaceToWorkspaceDTOandBack() {
        UUID workspaceId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Workspace workspace = new Workspace();
        workspace.setId(workspaceId);
        workspace.setName("Test Workspace");
        workspace.setDescription("Workspace Description");
        workspace.setCreatedAt(now);
        workspace.setUpdatedAt(now);

        Project project = new Project();
        project.setId(UUID.randomUUID());
        workspace.getProjects().add(project);

        Invitation invitation = new Invitation();
        invitation.setId(UUID.randomUUID());
        workspace.getInvitations().add(invitation);

        User owner = new User();
        owner.setId(UUID.randomUUID());
        workspace.setOwner(owner);

        WorkspaceDTO dto = workspaceMapper.toDTO(workspace);

        assertNotNull(dto);
        assertEquals(workspaceId, dto.getId());
        assertEquals("Test Workspace", dto.getName());
        assertEquals("Workspace Description", dto.getDescription());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
        assertEquals(owner.getId(), dto.getOwnerId());
        assertNotNull(dto.getProjectIds());
        assertTrue(dto.getProjectIds().contains(project.getId()));
        assertNotNull(dto.getInvitationIds());
        assertTrue(dto.getInvitationIds().contains(invitation.getId()));

        // Mock repositories for toEntity
        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
        InvitationRepository invitationRepository = Mockito.mock(InvitationRepository.class);
        Mockito.when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        Mockito.when(invitationRepository.findById(invitation.getId())).thenReturn(Optional.of(invitation));

        // Test with projectIds and invitationIds
        dto.setProjectIds(Set.of(project.getId()));
        dto.setInvitationIds(Set.of(invitation.getId()));
        Workspace workspaceFromDto = workspaceMapper.toEntity(dto, projectRepository, invitationRepository);
        assertNotNull(workspaceFromDto);
        assertNotNull(workspaceFromDto.getProjects());
        assertTrue(workspaceFromDto.getProjects().contains(project));
        assertNotNull(workspaceFromDto.getInvitations());
        assertTrue(workspaceFromDto.getInvitations().contains(invitation));
    }

    @Test
    public void testMapWorkspaceDTOToWorkspace() {
        UUID workspaceId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        WorkspaceDTO dto = new WorkspaceDTO();
        dto.setId(workspaceId);
        dto.setName("DTO Workspace");
        dto.setDescription("DTO Description");
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);
        dto.setOwnerId(UUID.randomUUID());

        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
        InvitationRepository invitationRepository = Mockito.mock(InvitationRepository.class);
        Workspace workspace = workspaceMapper.toEntity(dto, projectRepository, invitationRepository);

        assertNotNull(workspace);
        assertEquals(workspaceId, workspace.getId());
        assertEquals("DTO Workspace", workspace.getName());
        assertEquals("DTO Description", workspace.getDescription());
        assertEquals(now, workspace.getCreatedAt());
        assertEquals(now, workspace.getUpdatedAt());
        assertNotNull(workspace.getOwner());
        assertEquals(dto.getOwnerId(), workspace.getOwner().getId());
    }
}
