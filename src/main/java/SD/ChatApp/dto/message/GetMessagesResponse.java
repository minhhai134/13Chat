package SD.ChatApp.dto.message;

import SD.ChatApp.model.conversation.Message;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetMessagesResponse {
    List<Message> messages;
}
