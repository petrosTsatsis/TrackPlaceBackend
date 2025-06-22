package track.place.com.UnitTests.MapperTests;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import track.place.com.dto.AttachmentDTO;
import track.place.com.entities.Attachment;
import track.place.com.entities.Task;
import track.place.com.entities.User;
import track.place.com.util.mapper.AttachmentMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AttachmentMapperTest {
    private AttachmentMapper attachmentMapper = Mappers.getMapper(AttachmentMapper.class);

    @Test
    public void testMapAttachmentToAttachmentDTO() {
        UUID attachmentId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Attachment attachment = new Attachment();
        attachment.setId(attachmentId);
        attachment.setFilename("file.txt");
        attachment.setUrl("http://example.com/file.txt");
        attachment.setUploadedAt(now);

        User uploader = new User();
        uploader.setId(UUID.randomUUID());
        attachment.setUploadedBy(uploader);

        Task task = new Task();
        task.setId(UUID.randomUUID());
        attachment.setTask(task);

        AttachmentDTO dto = attachmentMapper.toDTO(attachment);

        assertNotNull(dto);
        assertEquals(attachmentId, dto.getId());
        assertEquals("file.txt", dto.getFilename());
        assertEquals("http://example.com/file.txt", dto.getUrl());
        assertEquals(now, dto.getUploadedAt());
        assertEquals(uploader.getId(), dto.getUploadedById());
        assertEquals(task.getId(), dto.getTaskId());
    }

    @Test
    public void testMapAttachmentDTOToAttachment() {
        UUID attachmentId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        AttachmentDTO dto = new AttachmentDTO();
        dto.setId(attachmentId);
        dto.setFilename("dto.txt");
        dto.setUrl("http://example.com/dto.txt");
        dto.setUploadedAt(now);
        dto.setUploadedById(UUID.randomUUID());
        dto.setTaskId(UUID.randomUUID());

        Attachment attachment = attachmentMapper.toEntity(dto);

        assertNotNull(attachment);
        assertEquals(attachmentId, attachment.getId());
        assertEquals("dto.txt", attachment.getFilename());
        assertEquals("http://example.com/dto.txt", attachment.getUrl());
        assertEquals(now, attachment.getUploadedAt());
        assertNotNull(attachment.getUploadedBy());
        assertNotNull(attachment.getTask());
        // uploadedBy and task will be null unless you handle mapping by id
    }
}

