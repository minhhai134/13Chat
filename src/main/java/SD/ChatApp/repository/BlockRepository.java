package SD.ChatApp.repository;

import SD.ChatApp.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, String> {

    Optional<Block> findByBlockerUserIdAndDestinationUserId(String blockerUserId, String destinationUserId);
}
