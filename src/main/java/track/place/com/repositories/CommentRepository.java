package track.place.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import track.place.com.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /*
     * TODO:
     *  findByTaskId(Long taskId);
     * */
}
