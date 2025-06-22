package track.place.com.UnitTests.MapperTests;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import track.place.com.dto.BoardListDTO;
import track.place.com.entities.Board;
import track.place.com.entities.BoardList;
import track.place.com.entities.Task;
import track.place.com.repositories.TaskRepository;
import track.place.com.util.mapper.BoardListMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BoardListMapperTest {
    private BoardListMapper boardListMapper = Mappers.getMapper(BoardListMapper.class);

    @Test
    public void testMapBoardListToBoardListDTOandBack() {
        UUID boardListId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        BoardList boardList = new BoardList();
        boardList.setId(boardListId);
        boardList.setName("Test List");
        boardList.setPosition(1);
        boardList.setCreatedAt(now);
        boardList.setUpdatedAt(now);

        Board board = new Board();
        board.setId(UUID.randomUUID());
        boardList.setBoard(board);

        // Tasks
        Task task = new Task();
        task.setId(UUID.randomUUID());
        boardList.setTasks(Set.of(task));

        // toDTO
        BoardListDTO dto = boardListMapper.toDTO(boardList);
        assertNotNull(dto);
        assertEquals(boardListId, dto.getId());
        assertEquals("Test List", dto.getName());
        assertEquals(1, dto.getPosition());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
        assertEquals(board.getId(), dto.getBoardId());
        assertTrue(dto.getTaskIds().contains(task.getId()));

        // toEntity with repository
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        dto.setTaskIds(Set.of(task.getId()));
        BoardList mappedBoardList = boardListMapper.toEntity(dto, taskRepository);
        assertNotNull(mappedBoardList);
        assertEquals(boardListId, mappedBoardList.getId());
        assertEquals("Test List", mappedBoardList.getName());
        assertEquals(1, mappedBoardList.getPosition());
        assertEquals(now, mappedBoardList.getCreatedAt());
        assertEquals(now, mappedBoardList.getUpdatedAt());
        assertNotNull(mappedBoardList.getTasks());
        assertTrue(mappedBoardList.getTasks().contains(task));
    }
}
