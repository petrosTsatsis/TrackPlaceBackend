package track.place.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import track.place.com.entities.Membership;

import java.util.UUID;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, UUID> {}