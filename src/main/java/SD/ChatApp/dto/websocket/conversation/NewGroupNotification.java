package SD.ChatApp.dto.websocket.conversation;

import SD.ChatApp.dto.conversation.common.GroupConversationDto;
import SD.ChatApp.dto.websocket.BaseNotification;
import SD.ChatApp.model.enums.Notification_Type;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewGroupNotification {
    private final Notification_Type notificationType;
    private GroupConversationDto newConversation;
}
