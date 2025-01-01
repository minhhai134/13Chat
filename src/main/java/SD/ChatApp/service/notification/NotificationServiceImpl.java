package SD.ChatApp.service.notification;

import SD.ChatApp.model.User;
import SD.ChatApp.model.notification.Notification;
import SD.ChatApp.repository.notification.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Notification sendNotification(Notification notification, User receiver){
        Notification savedNotification = notificationRepository.save(notification);

        messagingTemplate.convertAndSendToUser(
                receiver.getUsername(), "/queue/messages", savedNotification);

        return savedNotification;
    }

    public Notification updateSeenStatus(Principal principal, Notification notification){
        notification.setSeenStatus(true);
        notificationRepository.save(notification);
        return null;
    }
}
