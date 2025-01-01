package SD.ChatApp.dto.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeenNotificationRequest {
    private String notificationId;
}
