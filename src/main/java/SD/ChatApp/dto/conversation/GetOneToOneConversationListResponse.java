package SD.ChatApp.dto.conversation;

import SD.ChatApp.model.enums.Conversation_Type;
import SD.ChatApp.model.enums.Membership_Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;


@Data
@Builder
@AllArgsConstructor
public class GetOneToOneConversationListResponse {

    private String id;

    private Conversation_Type conversationType;

    private Instant conversationLastActive;

//    private Membership_Status status; Status nay de lam gi vay??

    private Instant friendLastSeen;

    private String friendId;

//  private String friendAvt;

    private String friendName;

}
