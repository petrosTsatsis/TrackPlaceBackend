package track.place.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import track.place.com.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /*
     * TODO:
     *  findByProjectId(Long projectId);
     * */
}
