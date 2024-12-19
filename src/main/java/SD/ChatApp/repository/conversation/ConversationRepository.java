package SD.ChatApp.repository.conversation;

import SD.ChatApp.dto.conversation.GetOneToOneConversationListResponse;
import SD.ChatApp.model.conversation.Conversation;
import SD.ChatApp.model.enums.Membership_Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {

    @Query(value =
            "select new SD.ChatApp.dto.conversation.GetOneToOneConversationListResponse(" +
                    "cv.id, cv.type, cv.lastActive, ms.lastSeen, u.id, u.name) " +
                    "from Conversation cv, Membership ms, User u " +
                    "where cv.id = ms.conversationId and u.id = ms.userId " +
                    "and ms.id not in (select ms2.id from Membership ms2 " +
                                      " where ms2.userId = :id) " +
                    "and ms.conversationId in (select ms3.conversationId from Membership ms3 " +
                                       "where ms3.userId = :id " +
                                       "and ms3.status = :status) " +
                    "and cv.type = 0 " +
                    "order by cv.lastActive desc limit 10" )
    List<GetOneToOneConversationListResponse> GetOnetoOneConversationList(
            @Param("id")String userId,
            @Param("status") Membership_Status memberShip_status);
}
