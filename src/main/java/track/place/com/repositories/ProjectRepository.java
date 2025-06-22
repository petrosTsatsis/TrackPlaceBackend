package track.place.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import track.place.com.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /*
     * TODO:
     *  findByWorkspaceId(Long workspaceId);
     * */
}
