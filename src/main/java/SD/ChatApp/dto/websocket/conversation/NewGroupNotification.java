package SD.ChatApp.dto.websocket.conversation;

import SD.ChatApp.dto.conversation.common.GroupConversationDto;
import SD.ChatApp.model.enums.Notification_Type;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewGroupNotification {
    private static final Notification_Type notificationType = Notification_Type.ADDED_TO_A_GROUP;
    private GroupConversationDto newGroup;
}
