package track.place.com.UnitTests.MapperTests;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import track.place.com.dto.CommentDTO;
import track.place.com.entities.Comment;
import track.place.com.entities.Task;
import track.place.com.entities.User;
import track.place.com.util.mapper.CommentMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommentMapperTest {
    private CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    public void testMapCommentToCommentDTO() {
        UUID commentId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setContent("Test comment");
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);

        Task task = new Task();
        task.setId(UUID.randomUUID());
        comment.setTask(task);

        User author = new User();
        author.setId(UUID.randomUUID());
        comment.setAuthor(author);

        CommentDTO dto = commentMapper.toDTO(comment);

        assertNotNull(dto);
        assertEquals(commentId, dto.getId());
        assertEquals("Test comment", dto.getContent());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
        assertEquals(task.getId(), dto.getTaskId());
        assertEquals(author.getId(), dto.getAuthorId());
    }

    @Test
    public void testMapCommentDTOToComment() {
        UUID commentId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        CommentDTO dto = new CommentDTO();
        dto.setId(commentId);
        dto.setContent("DTO comment");
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);
        dto.setTaskId(UUID.randomUUID());
        dto.setAuthorId(UUID.randomUUID());

        Comment comment = commentMapper.toEntity(dto);

        assertNotNull(comment);
        assertEquals(commentId, comment.getId());
        assertEquals("DTO comment", comment.getContent());
        assertEquals(now, comment.getCreatedAt());
        assertEquals(now, comment.getUpdatedAt());
        assertNotNull(comment.getTask());
        assertNotNull(comment.getAuthor());
        // task and author will be null unless you handle mapping by id
    }
}

