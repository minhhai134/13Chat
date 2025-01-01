package SD.ChatApp.dto.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeenNotificationResponse {
    private String status;
}
