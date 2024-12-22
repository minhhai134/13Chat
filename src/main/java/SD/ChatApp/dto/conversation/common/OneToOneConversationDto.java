package SD.ChatApp.dto.conversation.common;

import SD.ChatApp.model.enums.Conversation_Type;
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

//    private Membership_Status status; Status nay de lam gi vay??

    private Instant friendLastSeen;

    public OneToOneConversationDto(String id, Conversation_Type conversationType, Instant conversationLastActive, Instant friendLastSeen, String friendId, String friendName) {
        this.conversationId = id;
        this.conversationType = conversationType;
        this.conversationLastActive = conversationLastActive;
        this.friendLastSeen = friendLastSeen;
        this.friendId = friendId;
        this.friendName = friendName;
    }

    private String friendId;

//  private String friendAvt;

    private String friendName;

    private String blocker;

}
