package SD.ChatApp.dto.websocket.message;

import SD.ChatApp.enums.Conversation_Type;
import SD.ChatApp.enums.Membership_Status;
import SD.ChatApp.enums.Message_Type;
import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class ChatMessageSending {

    private String conversationId;

    private Conversation_Type conversationType;
//
//    private String senderId;

    private String destinationId;

    private Instant sentTime;

    private Message_Type messageType;

    /*
    Membership_Status cua sender
     */
    private Membership_Status membershipStatus;

    private String content;
}
