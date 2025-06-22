package track.place.com.UnitTests.MapperTests;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import track.place.com.dto.BoardDTO;
import track.place.com.entities.Board;
import track.place.com.entities.BoardList;
import track.place.com.entities.Project;
import track.place.com.entities.enums.BoardType;
import track.place.com.repositories.BoardListRepository;
import track.place.com.util.mapper.BoardMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BoardMapperTest {
    private BoardMapper boardMapper = Mappers.getMapper(BoardMapper.class);

    @Test
    public void testMapBoardToBoardDTOandBack() {
        UUID boardId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Board board = new Board();
        board.setId(boardId);
        board.setName("Test Board");
        board.setType(BoardType.KANBAN);
        board.setCreatedAt(now);
        board.setUpdatedAt(now);

        Project project = new Project();
        project.setId(UUID.randomUUID());
        board.setProject(project);

        // BoardLists
        BoardList boardList = new BoardList();
        boardList.setId(UUID.randomUUID());
        board.setBoardLists(Set.of(boardList));

        // toDTO
        BoardDTO dto = boardMapper.toDTO(board);
        assertNotNull(dto);
        assertEquals(boardId, dto.getId());
        assertEquals("Test Board", dto.getName());
        assertEquals(BoardType.KANBAN, dto.getType());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
        assertEquals(project.getId(), dto.getProjectId());
        assertTrue(dto.getBoardListIds().contains(boardList.getId()));

        // toEntity with repository
        BoardListRepository boardListRepository = Mockito.mock(BoardListRepository.class);
        Mockito.when(boardListRepository.findById(boardList.getId())).thenReturn(Optional.of(boardList));
        dto.setBoardListIds(Set.of(boardList.getId()));
        Board mappedBoard = boardMapper.toEntity(dto, boardListRepository);
        assertNotNull(mappedBoard);
        assertEquals(boardId, mappedBoard.getId());
        assertEquals("Test Board", mappedBoard.getName());
        assertEquals(BoardType.KANBAN, mappedBoard.getType());
        assertEquals(now, mappedBoard.getCreatedAt());
        assertEquals(now, mappedBoard.getUpdatedAt());
        assertNotNull(mappedBoard.getBoardLists());
        assertTrue(mappedBoard.getBoardLists().contains(boardList));
    }
}
