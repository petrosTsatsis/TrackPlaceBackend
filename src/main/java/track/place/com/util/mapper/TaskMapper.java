package track.place.com.util.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.MappingTarget;
import track.place.com.dto.TaskDTO;
import track.place.com.entities.Attachment;
import track.place.com.entities.Comment;
import track.place.com.entities.Task;
import track.place.com.repositories.AttachmentRepository;
import track.place.com.repositories.CommentRepository;

import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mappings({
            @Mapping(source = "project.id", target = "projectId"),
            @Mapping(source = "boardList.id", target = "boardListId"),
            @Mapping(source = "assignee.id", target = "assigneeId"),
            @Mapping(target = "attachmentIds", expression = "java(task.getAttachments() != null ? task.getAttachments().stream().map(a -> a.getId()).collect(java.util.stream.Collectors.toSet()) : null)"),
            @Mapping(target = "commentIds", expression = "java(task.getComments() != null ? task.getComments().stream().map(c -> c.getId()).collect(java.util.stream.Collectors.toSet()) : null)")
    })
    TaskDTO toDTO(Task task);

    @Mappings({
            @Mapping(target = "project.id", source = "projectId"),
            @Mapping(target = "boardList.id", source = "boardListId"),
            @Mapping(target = "assignee.id", source = "assigneeId")
    })
    Task toEntity(TaskDTO dto, @Context AttachmentRepository attachmentRepository, @Context CommentRepository commentRepository);

    @AfterMapping
    default void mapAttachmentsAndComments(TaskDTO dto, @MappingTarget Task entity, @Context AttachmentRepository attachmentRepository, @Context CommentRepository commentRepository) {
        if (dto.getAttachmentIds() != null) {
            Set<Attachment> attachments = new java.util.HashSet<>();
            for (UUID id : dto.getAttachmentIds()) {
                attachmentRepository.findById(id).ifPresent(attachments::add);
            }
            entity.setAttachments(attachments);
        }
        if (dto.getCommentIds() != null) {
            Set<Comment> comments = new java.util.HashSet<>();
            for (UUID id : dto.getCommentIds()) {
                commentRepository.findById(id).ifPresent(comments::add);
            }
            entity.setComments(comments);
        }
    }
}
