package track.place.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import track.place.com.entities.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    /*
     * TODO:
     *  findByProjectId(Long projectId);
     * */
}
