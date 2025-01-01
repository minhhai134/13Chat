package SD.ChatApp.dto.notification;

import SD.ChatApp.model.notification.Notification;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetNotificationListResponse {
    List<Notification> notificationList;
}
