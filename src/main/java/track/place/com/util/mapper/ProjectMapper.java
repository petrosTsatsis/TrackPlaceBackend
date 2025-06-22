package track.place.com.util.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.MappingTarget;
import track.place.com.dto.ProjectDTO;
import track.place.com.entities.Project;
import track.place.com.entities.Board;
import track.place.com.entities.Task;
import track.place.com.entities.Invitation;
import track.place.com.entities.GitHubIntegration;
import track.place.com.repositories.BoardRepository;
import track.place.com.repositories.GitHubIntegrationRepository;
import track.place.com.repositories.TaskRepository;
import track.place.com.repositories.InvitationRepository;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    @Mappings({
        @Mapping(source = "workspace.id", target = "workspaceId"),
        @Mapping(source = "projectLead.id", target = "projectLeadId"),
        @Mapping(target = "boardIds", expression = "java(entity.getBoards() != null ? entity.getBoards().stream().map(b -> b.getId()).collect(java.util.stream.Collectors.toSet()) : new java.util.HashSet<>())"),
        @Mapping(target = "taskIds", expression = "java(entity.getTasks() != null ? entity.getTasks().stream().map(t -> t.getId()).collect(java.util.stream.Collectors.toSet()) : new java.util.HashSet<>())"),
        @Mapping(target = "invitationIds", expression = "java(entity.getInvitations() != null ? entity.getInvitations().stream().map(i -> i.getId()).collect(java.util.stream.Collectors.toSet()) : new java.util.HashSet<>())"),
        @Mapping(target = "githubIntegrationIds", expression = "java(entity.getGithubIntegrations() != null ? entity.getGithubIntegrations().stream().map(g -> g.getId()).collect(java.util.stream.Collectors.toSet()) : new java.util.HashSet<>())")
    })
    ProjectDTO toDTO(Project entity);

    @Mappings({
        @Mapping(target = "workspace.id", source = "workspaceId"),
        @Mapping(target = "projectLead.id", source = "projectLeadId")
    })
    Project toEntity(ProjectDTO dto, @Context BoardRepository boardRepository, @Context TaskRepository taskRepository, @Context InvitationRepository invitationRepository, @Context GitHubIntegrationRepository gitHubIntegrationRepository);

    @AfterMapping
    default void mapSets(ProjectDTO dto, @MappingTarget Project entity, @Context BoardRepository boardRepository, @Context TaskRepository taskRepository, @Context InvitationRepository invitationRepository, @Context GitHubIntegrationRepository gitHubIntegrationRepository) {
        if (dto.getBoardIds() != null) {
            Set<Board> boards = new java.util.HashSet<>();
            for (java.util.UUID id : dto.getBoardIds()) {
                boardRepository.findById(id).ifPresent(boards::add);
            }
            entity.setBoards(boards);
        }
        if (dto.getTaskIds() != null) {
            Set<Task> tasks = new java.util.HashSet<>();
            for (java.util.UUID id : dto.getTaskIds()) {
                taskRepository.findById(id).ifPresent(tasks::add);
            }
            entity.setTasks(tasks);
        }
        if (dto.getInvitationIds() != null) {
            Set<Invitation> invitations = new java.util.HashSet<>();
            for (java.util.UUID id : dto.getInvitationIds()) {
                invitationRepository.findById(id).ifPresent(invitations::add);
            }
            entity.setInvitations(invitations);
        }
        if (dto.getGithubIntegrationIds() != null) {
            Set<GitHubIntegration> integrations = new java.util.HashSet<>();
            for (java.util.UUID id : dto.getGithubIntegrationIds()) {
                gitHubIntegrationRepository.findById(id).ifPresent(integrations::add);
            }
            entity.setGithubIntegrations(integrations);
        }
    }
}
