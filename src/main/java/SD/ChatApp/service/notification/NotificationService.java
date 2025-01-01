package SD.ChatApp.service.notification;

import SD.ChatApp.model.User;
import SD.ChatApp.model.notification.Notification;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface NotificationService {
    Notification sendNotification(Notification notification, User receiver);

    Notification updateSeenStatus(Principal principal, Notification notification);
}
