package SD.ChatApp.repository.conversation;

import SD.ChatApp.model.conversation.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, String> {
}
