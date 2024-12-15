package SD.ChatApp.repository.network;

import SD.ChatApp.model.network.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, String> {

    Optional<Block> findByBlockerUserIdAndDestinationUserId(String blockerUserId, String destinationUserId);
}
