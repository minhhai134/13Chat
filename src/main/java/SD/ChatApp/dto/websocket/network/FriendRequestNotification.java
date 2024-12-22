package SD.ChatApp.dto.websocket.network;

import SD.ChatApp.model.enums.Notification_Type;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendRequestNotification {
    private Notification_Type notificationType;
    private String senderId;
    private String senderName;
    private String senderAvt;
}
