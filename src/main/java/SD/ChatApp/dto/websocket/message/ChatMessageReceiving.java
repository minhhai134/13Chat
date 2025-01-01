package SD.ChatApp.dto.websocket.message;

import SD.ChatApp.model.conversation.Message;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageReceiving {
    private Message message;

    private String destinationId;

//    private String senderName;
//
//    private String senderAvt;
}


