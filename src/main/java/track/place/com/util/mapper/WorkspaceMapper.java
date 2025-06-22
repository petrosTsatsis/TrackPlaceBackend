package track.place.com.util.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.MappingTarget;
import track.place.com.dto.WorkspaceDTO;
import track.place.com.entities.Project;
import track.place.com.entities.Workspace;
import track.place.com.repositories.ProjectRepository;
import track.place.com.repositories.InvitationRepository;

import java.util.Set;


@Mapper(componentModel = "spring")
public interface WorkspaceMapper {
    @Mappings({
        @Mapping(source = "owner.id", target = "ownerId"),
        @Mapping(target = "projectIds", expression = "java(entity.getProjects() != null ? entity.getProjects().stream().map(p -> p.getId()).collect(java.util.stream.Collectors.toSet()) : new java.util.HashSet<>())"),
        @Mapping(target = "invitationIds", expression = "java(entity.getInvitations() != null ? entity.getInvitations().stream().map(i -> i.getId()).collect(java.util.stream.Collectors.toSet()) : new java.util.HashSet<>())")
    })
    WorkspaceDTO toDTO(Workspace entity);

    @Mappings({
        @Mapping(target = "owner.id", source = "ownerId")
    })
    Workspace toEntity(WorkspaceDTO dto, @Context ProjectRepository projectRepository, @Context InvitationRepository invitationRepository);

    @AfterMapping
    default void mapProjectsAndInvitations(WorkspaceDTO dto, @MappingTarget Workspace entity, @Context ProjectRepository projectRepository, @Context InvitationRepository invitationRepository) {
        if (dto.getProjectIds() != null) {
            Set<Project> projects = new java.util.HashSet<>();
            for (java.util.UUID id : dto.getProjectIds()) {
                projectRepository.findById(id).ifPresent(projects::add);
            }
            entity.setProjects(projects);
        }
        if (dto.getInvitationIds() != null) {
            Set<track.place.com.entities.Invitation> invitations = new java.util.HashSet<>();
            for (java.util.UUID id : dto.getInvitationIds()) {
                invitationRepository.findById(id).ifPresent(invitations::add);
            }
            entity.setInvitations(invitations);
        }
    }
}
