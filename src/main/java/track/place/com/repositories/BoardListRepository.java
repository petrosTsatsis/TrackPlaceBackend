package track.place.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import track.place.com.entities.BoardList;

import java.util.UUID;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, UUID> {

    /*
     * TODO:
     *  findByBoardId(Long boardId);
     * */
}
