package SD.ChatApp.repository.conversation;

import SD.ChatApp.model.conversation.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, String> {

    Optional<Membership> findByConversationIdAndUserId(String conversationId, String userId);

    @Transactional
    void deleteByConversationIdAndUserId(String conversationId, String userId);
}
