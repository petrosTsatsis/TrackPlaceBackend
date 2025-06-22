package track.place.com.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import track.place.com.dto.InvitationDTO;
import track.place.com.entities.Invitation;

@Mapper(componentModel = "spring")
public interface InvitationMapper {
    @Mappings({
        @Mapping(source = "workspace.id", target = "workspaceId"),
        @Mapping(source = "project.id", target = "projectId")
    })
    InvitationDTO toDTO(Invitation entity);

    @Mappings({
        @Mapping(target = "workspace.id", source = "workspaceId"),
        @Mapping(target = "project.id", source = "projectId")
    })
    Invitation toEntity(InvitationDTO dto);
}

