package track.place.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import track.place.com.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
