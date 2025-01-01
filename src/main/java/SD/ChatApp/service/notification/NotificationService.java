package SD.ChatApp.service.notification;

import SD.ChatApp.dto.notification.SeenNotificationRequest;
import SD.ChatApp.dto.notification.SeenNotificationResponse;
import SD.ChatApp.model.User;
import SD.ChatApp.model.notification.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Service
public interface NotificationService {
    Notification sendNotification(Notification notification, User receiver);

    Notification updateSeenStatus(Principal principal, Notification notification);

    List<Notification> getNotificationList(Principal principal);

    SeenNotificationResponse seenNotification(
            Principal principal,
            SeenNotificationRequest request);
}
