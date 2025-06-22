package track.place.com.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import track.place.com.dto.AttachmentDTO;
import track.place.com.entities.Attachment;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    @Mappings({
        @Mapping(source = "uploadedBy.id", target = "uploadedById"),
        @Mapping(source = "task.id", target = "taskId")
    })
    AttachmentDTO toDTO(Attachment attachment);

    @Mappings({
        @Mapping(target = "uploadedBy.id", source = "uploadedById"),
        @Mapping(target = "task.id", source = "taskId")
    })
    Attachment toEntity(AttachmentDTO dto);
}

