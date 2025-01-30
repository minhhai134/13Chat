package SD.ChatApp.dto.websocket.conversation;

import SD.ChatApp.dto.conversation.common.OneToOneConversationDto;
import SD.ChatApp.enums.Notification_Type;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewOneToOneChatNotification {
    private final Notification_Type notificationType;
    private OneToOneConversationDto newConversation;
}
