package track.place.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import track.place.com.entities.GitHubIntegration;

import java.util.UUID;

@Repository
public interface GitHubIntegrationRepository extends JpaRepository<GitHubIntegration, UUID> {
}
