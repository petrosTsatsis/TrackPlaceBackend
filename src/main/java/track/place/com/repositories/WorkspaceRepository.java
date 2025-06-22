package track.place.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import track.place.com.entities.Workspace;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {}
