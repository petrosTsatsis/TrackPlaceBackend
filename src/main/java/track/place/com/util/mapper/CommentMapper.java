package track.place.com.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import track.place.com.dto.CommentDTO;
import track.place.com.entities.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mappings({
        @Mapping(source = "task.id", target = "taskId"),
        @Mapping(source = "author.id", target = "authorId")
    })
    CommentDTO toDTO(Comment comment);

    @Mappings({
        @Mapping(target = "task.id", source = "taskId"),
        @Mapping(target = "author.id", source = "authorId")
    })
    Comment toEntity(CommentDTO dto);
}

