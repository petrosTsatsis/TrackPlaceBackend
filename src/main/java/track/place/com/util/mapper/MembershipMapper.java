package track.place.com.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import track.place.com.dto.MembershipDTO;
import track.place.com.entities.Membership;

@Mapper(componentModel = "spring")
public interface MembershipMapper {
    @Mappings({
        @Mapping(source = "user.id", target = "userId"),
        @Mapping(source = "workspace.id", target = "workspaceId"),
        @Mapping(source = "project.id", target = "projectId")
    })
    MembershipDTO toDTO(Membership entity);

    @Mappings({
        @Mapping(target = "user.id", source = "userId"),
        @Mapping(target = "workspace.id", source = "workspaceId"),
        @Mapping(target = "project.id", source = "projectId")
    })
    Membership toEntity(MembershipDTO dto);
}

