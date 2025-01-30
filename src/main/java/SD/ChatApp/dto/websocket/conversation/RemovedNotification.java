package SD.ChatApp.dto.websocket.conversation;

import SD.ChatApp.enums.Notification_Type;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemovedNotification {
    private final Notification_Type notificationType;
    private String groupId;
}
