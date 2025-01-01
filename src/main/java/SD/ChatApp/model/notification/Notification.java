package SD.ChatApp.model.notification;

import SD.ChatApp.model.enums.Notification_Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Notification_Type notificationType;

    private String userId;

    private String notificationContent;

    private boolean seenStatus;
}
