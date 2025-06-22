package track.place.com.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import track.place.com.dto.GitHubIntegrationDTO;
import track.place.com.entities.GitHubIntegration;

@Mapper(componentModel = "spring")
public interface GitHubIntegrationMapper {
    @Mappings({
        @Mapping(source = "project.id", target = "projectId")
    })
    GitHubIntegrationDTO toDTO(GitHubIntegration entity);

    @Mappings({
        @Mapping(target = "project.id", source = "projectId")
    })
    GitHubIntegration toEntity(GitHubIntegrationDTO dto);
}

