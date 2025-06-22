package track.place.com.util.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.MappingTarget;
import track.place.com.dto.BoardListDTO;
import track.place.com.entities.BoardList;
import track.place.com.entities.Task;
import track.place.com.repositories.TaskRepository;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface BoardListMapper {
    @Mappings({
        @Mapping(source = "board.id", target = "boardId"),
        @Mapping(target = "taskIds", expression = "java(boardList.getTasks() != null ? boardList.getTasks().stream().map(t -> t.getId()).collect(java.util.stream.Collectors.toSet()) : new java.util.HashSet<>())")
    })
    BoardListDTO toDTO(BoardList boardList);

    @Mappings({
        @Mapping(target = "board.id", source = "boardId")
    })
    BoardList toEntity(BoardListDTO dto, @Context TaskRepository taskRepository);

    @AfterMapping
    default void mapTasks(BoardListDTO dto, @MappingTarget BoardList entity, @Context TaskRepository taskRepository) {
        if (dto.getTaskIds() != null) {
            Set<Task> tasks = new java.util.HashSet<>();
            for (java.util.UUID id : dto.getTaskIds()) {
                taskRepository.findById(id).ifPresent(tasks::add);
            }
            entity.setTasks(tasks);
        }
    }
}
