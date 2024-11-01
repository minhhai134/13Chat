package SD.ChatApp.repository;

import SD.ChatApp.model.FriendRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<FriendRelation, String> {
}
