package SD.ChatApp.repository.conversation;

import SD.ChatApp.dto.conversation.common.GroupConversationDto;
import SD.ChatApp.dto.conversation.common.OneToOneConversationDto;
import SD.ChatApp.dto.conversation.group.GetGroupMemberResponse;
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
            "select new SD.ChatApp.dto.conversation.common.OneToOneConversationDto(" +
                    "cv.id, cv.type, cv.lastActive, cv.lastMessageID, cv.lastMessageContent, ms.lastSeen, u.id, u.name) " +
                    "from Conversation cv, Membership ms, User u " +
                    "where cv.id = ms.conversationId and u.id = ms.userId " +
                    "and ms.id not in (select ms2.id from Membership ms2 " +
                                      " where ms2.userId = :id) " +
                    "and ms.conversationId in (select ms3.conversationId from Membership ms3 " +
                                       "where ms3.userId = :id " +
                                       "and ms3.status = :status) " +
                    "and cv.type = 0 " +
                    "order by cv.lastActive desc limit 10" )
    List<OneToOneConversationDto> getOnetoOneConversationList(
            @Param("id")String userId,
            @Param("status") Membership_Status memberShip_status);

    @Query(value =
            "select new SD.ChatApp.dto.conversation.common.OneToOneConversationDto(" +
                    "cv.id, cv.type, cv.lastActive, cv.lastMessageID, cv.lastMessageContent, ms.lastSeen, u.id, u.name,  u.avatar) " +
                    "from Conversation cv, Membership ms, User u " +
                    "where cv.id = ms.conversationId and u.id = ms.userId " +
                    "and cv.id = :id and u.id <> :userId ") // +
//                    "and ms.id not in (select ms2.id from Membership ms2 " +
//                    " where ms2.userId = :userId) " +
//                    "and ms.conversationId in (select ms3.conversationId from Membership ms3 " +
//                    "where ms3.userId = :id ") // +
//                    "and ms3.status = :status) " +
//                    "and cv.type = 0 " +
//                    "order by cv.lastActive desc limit 10" )
    List<OneToOneConversationDto> getOnetoOneConversationById(@Param("id") String groupId, @Param("userId") String userId);


    @Query(value =
            "select new SD.ChatApp.dto.conversation.common.GroupConversationDto(" +
            "cv.id, cv.type, cv.lastActive, cv.lastMessageID, cv.lastMessageContent, ms.lastSeen, ms.id, mt.adminId, mt.groupName) " +
            "from Conversation cv, Membership ms, GroupMetaData mt, User u " +
            "where cv.id = ms.conversationId and cv.id = mt.groupId and u.id=ms.userId " +
//            "and cv.id in (select ms2.conversationId from Membership ms2 where ms2.userId = :id and ms2.status = :status) " +
            "and u.id=:id and ms.status = :status " +
            "and cv.type = 1 " +
            "order by cv.lastActive desc limit 10 ")
    List<GroupConversationDto> getGroupConversationList(
            @Param("id")String userId,
            @Param("status") Membership_Status memberShip_status
    );

    @Query(value =
            "select new SD.ChatApp.dto.conversation.common.GroupConversationDto(" +
                    "cv.id, cv.type, cv.lastActive, cv.lastMessageID, cv.lastMessageContent, ms.lastSeen, ms.id, mt.adminId, mt.groupName) " +
                    "from Conversation cv, Membership ms, GroupMetaData mt, User u " +
                    "where cv.id = ms.conversationId and cv.id = mt.groupId and u.id=ms.userId " +
                    "and cv.id = :id  " +
//            "and cv.id in (select ms2.conversationId from Membership ms2 where ms2.userId = :id and ms2.status = :status) " +
                    "and u.id=:userId")  // and ms.status = :status " )
//                    "and cv.type = 1 ")
//                    "order by cv.lastActive desc limit 10 ")
    List<GroupConversationDto> getGroupById(@Param("id") String groupId, @Param("userId") String userId);

    @Query(value =
            "select new SD.ChatApp.dto.conversation.group.GetGroupMemberResponse(" +
                    "u.id, u.name) " +
                    "from User u, Membership ms where u.id = ms.userId " +
                    "and ms.conversationId = :conversationId")
    List<GetGroupMemberResponse> getMemberList(@Param("conversationId") String conversationId);

    @Query(value =
            "select ms.conversationId from Membership ms, Conversation cv " +
            "where cv.id = ms.conversationId "+
            "and ms.conversationId in (select ms2.conversationId from Membership ms2 where ms2.userId=:userId) " +
            "and ms.userId = :friendId "     +
            "and cv.type = 0")
    List<String> checkConversationExisted(String userId, String friendId);

}
