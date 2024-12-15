package SD.ChatApp.dto.message;

import SD.ChatApp.model.conversation.Message;
import SD.ChatApp.model.entity.Message_Type;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ChatMessageReceiving {
    private Message message;

    private String receiverId;
}


