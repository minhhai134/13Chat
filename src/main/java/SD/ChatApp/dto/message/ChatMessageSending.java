package SD.ChatApp.dto.message;

import SD.ChatApp.model.entity.Message_Type;
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

    private String content;
}
