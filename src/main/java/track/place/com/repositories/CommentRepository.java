package track.place.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import track.place.com.entities.Comment;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    /*
     * TODO:
     *  findByTaskId(Long taskId);
     * */
}
