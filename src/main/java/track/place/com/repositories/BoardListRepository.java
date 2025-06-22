package track.place.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import track.place.com.entities.BoardList;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {

    /*
     * TODO:
     *  findByBoardId(Long boardId);
     * */
}
