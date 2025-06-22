package track.place.com.UnitTests.MapperTests;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import track.place.com.dto.TaskDTO;
import track.place.com.entities.*;
import track.place.com.entities.enums.Label;
import track.place.com.entities.enums.TaskPriority;
import track.place.com.entities.enums.TaskStatus;
import track.place.com.repositories.AttachmentRepository;
import track.place.com.repositories.CommentRepository;
import track.place.com.util.mapper.TaskMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TaskMapperTest {

    private TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    public void testMapTaskToTaskDTOAndBack() {
        UUID taskId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setPriority(TaskPriority.HIGH);
        task.setStatus(TaskStatus.IN_PROGRESS);

        Project project = new Project();
        project.setId(UUID.randomUUID());
        task.setProject(project);

        BoardList boardList = new BoardList();
        boardList.setId(UUID.randomUUID());
        task.setBoardList(boardList);

        User assignee = new User();
        assignee.setId(UUID.randomUUID());
        task.setAssignee(assignee);

        task.setCreatedAt(now);
        task.setUpdatedAt(now);
        task.setTaskLabels(Set.of(Label.Bug, Label.Feature));

        // Attachments and Comments
        Attachment attachment = new Attachment();
        attachment.setId(UUID.randomUUID());
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        task.setAttachments(Set.of(attachment));
        task.setComments(Set.of(comment));

        // toDTO
        TaskDTO dto = taskMapper.toDTO(task);
        assertNotNull(dto);
        assertEquals(taskId, dto.getId());
        assertEquals("Test Task", dto.getTitle());
        assertEquals("Test Description", dto.getDescription());
        assertEquals(task.getDueDate(), dto.getDueDate());
        assertEquals(TaskPriority.HIGH, dto.getPriority());
        assertEquals(TaskStatus.IN_PROGRESS, dto.getStatus());
        assertEquals(project.getId(), dto.getProjectId());
        assertEquals(boardList.getId(), dto.getBoardListId());
        assertEquals(assignee.getId(), dto.getAssigneeId());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
        assertEquals(Set.of(Label.Bug, Label.Feature), dto.getTaskLabels());
        assertTrue(dto.getAttachmentIds().contains(attachment.getId()));
        assertTrue(dto.getCommentIds().contains(comment.getId()));

        // toEntity with repositories
        AttachmentRepository attachmentRepository = Mockito.mock(AttachmentRepository.class);
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        Mockito.when(attachmentRepository.findById(attachment.getId())).thenReturn(Optional.of(attachment));
        Mockito.when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        dto.setAttachmentIds(Set.of(attachment.getId()));
        dto.setCommentIds(Set.of(comment.getId()));
        Task mappedTask = taskMapper.toEntity(dto, attachmentRepository, commentRepository);
        assertNotNull(mappedTask);
        assertEquals(taskId, mappedTask.getId());
        assertEquals("Test Task", mappedTask.getTitle());
        assertEquals("Test Description", mappedTask.getDescription());
        assertEquals(task.getDueDate(), mappedTask.getDueDate());
        assertEquals(TaskPriority.HIGH, mappedTask.getPriority());
        assertEquals(TaskStatus.IN_PROGRESS, mappedTask.getStatus());
        assertEquals(now, mappedTask.getCreatedAt());
        assertEquals(now, mappedTask.getUpdatedAt());
        assertEquals(Set.of(Label.Bug, Label.Feature), mappedTask.getTaskLabels());
        assertTrue(mappedTask.getAttachments().contains(attachment));
        assertTrue(mappedTask.getComments().contains(comment));
    }
}
