package track.place.com.util.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.MappingTarget;
import track.place.com.dto.BoardDTO;
import track.place.com.entities.Board;
import track.place.com.entities.BoardList;
import track.place.com.repositories.BoardListRepository;

import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    @Mappings({
        @Mapping(source = "project.id", target = "projectId"),
        @Mapping(target = "boardListIds", expression = "java(board.getBoardLists() != null ? board.getBoardLists().stream().map(bl -> bl.getId()).collect(java.util.stream.Collectors.toSet()) : new java.util.HashSet<>())")
    })
    BoardDTO toDTO(Board board);

    @Mappings({
        @Mapping(target = "project.id", source = "projectId")
    })
    Board toEntity(BoardDTO dto, @Context BoardListRepository boardListRepository);

    @AfterMapping
    default void mapBoardLists(BoardDTO dto, @MappingTarget Board entity, @Context BoardListRepository boardListRepository) {
        if (dto.getBoardListIds() != null) {
            Set<BoardList> boardLists = new java.util.HashSet<>();
            for (UUID id : dto.getBoardListIds()) {
                boardListRepository.findById(id).ifPresent(boardLists::add);
            }
            entity.setBoardLists(boardLists);
        }
    }
}
