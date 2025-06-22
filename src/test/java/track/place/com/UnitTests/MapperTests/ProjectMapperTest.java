package track.place.com.UnitTests.MapperTests;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import track.place.com.dto.ProjectDTO;
import track.place.com.entities.*;
import track.place.com.repositories.BoardRepository;
import track.place.com.repositories.GitHubIntegrationRepository;
import track.place.com.repositories.InvitationRepository;
import track.place.com.repositories.TaskRepository;
import track.place.com.util.mapper.ProjectMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectMapperTest {
    private ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

    @Test
    public void testMapProjectToProjectDTOandBack() {
        UUID projectId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Project project = new Project();
        project.setId(projectId);
        project.setName("Test Project");
        project.setDescription("Project Description");
        project.setCreatedAt(now);
        project.setUpdatedAt(now);

        Workspace workspace = new Workspace();
        workspace.setId(UUID.randomUUID());
        project.setWorkspace(workspace);

        User projectLead = new User();
        projectLead.setId(UUID.randomUUID());
        project.setProjectLead(projectLead);

        // Boards, Tasks, Invitations, GitHubIntegrations
        Board board = new Board();
        board.setId(UUID.randomUUID());
        Task task = new Task();
        task.setId(UUID.randomUUID());
        Invitation invitation = new Invitation();
        invitation.setId(UUID.randomUUID());
        GitHubIntegration integration = new GitHubIntegration();
        integration.setId(UUID.randomUUID());
        project.setBoards(Set.of(board));
        project.setTasks(Set.of(task));
        project.setInvitations(Set.of(invitation));
        project.setGithubIntegrations(Set.of(integration));

        // toDTO
        ProjectDTO dto = projectMapper.toDTO(project);
        assertNotNull(dto);
        assertEquals(projectId, dto.getId());
        assertEquals("Test Project", dto.getName());
        assertEquals("Project Description", dto.getDescription());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
        assertEquals(workspace.getId(), dto.getWorkspaceId());
        assertEquals(projectLead.getId(), dto.getProjectLeadId());
        assertTrue(dto.getBoardIds().contains(board.getId()));
        assertTrue(dto.getTaskIds().contains(task.getId()));
        assertTrue(dto.getInvitationIds().contains(invitation.getId()));
        assertTrue(dto.getGithubIntegrationIds().contains(integration.getId()));

        // toEntity with repositories
        BoardRepository boardRepository = Mockito.mock(BoardRepository.class);
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        InvitationRepository invitationRepository = Mockito.mock(InvitationRepository.class);
        GitHubIntegrationRepository gitHubIntegrationRepository = Mockito.mock(GitHubIntegrationRepository.class);
        Mockito.when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        Mockito.when(invitationRepository.findById(invitation.getId())).thenReturn(Optional.of(invitation));
        Mockito.when(gitHubIntegrationRepository.findById(integration.getId())).thenReturn(Optional.of(integration));
        dto.setBoardIds(Set.of(board.getId()));
        dto.setTaskIds(Set.of(task.getId()));
        dto.setInvitationIds(Set.of(invitation.getId()));
        dto.setGithubIntegrationIds(Set.of(integration.getId()));
        Project mappedProject = projectMapper.toEntity(dto, boardRepository, taskRepository, invitationRepository, gitHubIntegrationRepository);
        assertNotNull(mappedProject);
        assertEquals(projectId, mappedProject.getId());
        assertEquals("Test Project", mappedProject.getName());
        assertEquals("Project Description", mappedProject.getDescription());
        assertEquals(now, mappedProject.getCreatedAt());
        assertEquals(now, mappedProject.getUpdatedAt());
        assertNotNull(mappedProject.getBoards());
        assertTrue(mappedProject.getBoards().contains(board));
        assertNotNull(mappedProject.getTasks());
        assertTrue(mappedProject.getTasks().contains(task));
        assertNotNull(mappedProject.getInvitations());
        assertTrue(mappedProject.getInvitations().contains(invitation));
        assertNotNull(mappedProject.getGithubIntegrations());
        assertTrue(mappedProject.getGithubIntegrations().contains(integration));
    }
}
