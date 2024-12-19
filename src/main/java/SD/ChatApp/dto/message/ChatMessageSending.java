package SD.ChatApp.dto.message;

import SD.ChatApp.model.enums.Membership_Status;
import SD.ChatApp.model.enums.Message_Type;
import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class ChatMessageSending {

    private String conversationId;
//
//    private String senderId;

    private String receiverId;

    private Instant sentTime;

    private Message_Type type;

    /*
    Membership_Status cua sender
     */
    private Membership_Status membershipStatus;

    private String content;
}
