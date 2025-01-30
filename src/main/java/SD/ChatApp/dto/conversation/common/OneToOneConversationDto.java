package SD.ChatApp.dto.conversation.common;

import SD.ChatApp.enums.Conversation_Type;
import SD.ChatApp.enums.Membership_Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;


@Data
@Builder
@AllArgsConstructor
public class OneToOneConversationDto {

    private String conversationId;

    private Conversation_Type conversationType;

    private Instant conversationLastActive;

    private long lastMessageID;

    private String lastMessageContent;

//    private Membership_Status status; Status nay de lam gi vay??

    private Instant friendLastSeen;

    public OneToOneConversationDto(String id, Conversation_Type conversationType, Instant conversationLastActive, long lastMessageID, String lastMessageContent, Instant friendLastSeen, String friendId, String friendName, String friendAvt) {
        this.conversationId = id;
        this.conversationType = conversationType;
        this.conversationLastActive = conversationLastActive;
        this.friendLastSeen = friendLastSeen;
        this.friendId = friendId;
        this.friendName = friendName;
        this.lastMessageID = lastMessageID;
        this.lastMessageContent = lastMessageContent;
        this.friendAvt = friendAvt;
//        this.membershipStatus = membershipStatus;
    }

    public OneToOneConversationDto(String id, Conversation_Type conversationType, Instant conversationLastActive, long lastMessageID, String lastMessageContent, Instant friendLastSeen, String friendId, String friendName) {
        this.conversationId = id;
        this.conversationType = conversationType;
        this.conversationLastActive = conversationLastActive;
        this.friendLastSeen = friendLastSeen;
        this.friendId = friendId;
        this.friendName = friendName;
        this.lastMessageID = lastMessageID;
        this.lastMessageContent = lastMessageContent;
//        this.friendAvt = friendAvt;
    }

    private String friendId;

    private String friendAvt;

    private String friendName;

    private String blocker;

    private Membership_Status membershipStatus;

}
