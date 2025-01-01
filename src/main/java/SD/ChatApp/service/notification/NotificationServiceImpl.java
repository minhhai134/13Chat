package SD.ChatApp.service.notification;

import SD.ChatApp.dto.notification.SeenNotificationRequest;
import SD.ChatApp.dto.notification.SeenNotificationResponse;
import SD.ChatApp.model.User;
import SD.ChatApp.model.notification.Notification;
import SD.ChatApp.repository.UserRepository;
import SD.ChatApp.repository.notification.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
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

    public List<Notification> getNotificationList(Principal principal){
        User user = userRepository.findByUsername(principal.getName()).get();
        return notificationRepository.getNotificationList(user.getId());
    }


    public SeenNotificationResponse seenNotification(Principal principal, SeenNotificationRequest request) {
        Notification notification = notificationRepository.findById(request.getNotificationId()).orElseThrow(RuntimeException::new);
        notification.setSeenStatus(true);
        notificationRepository.save(notification);
        return SeenNotificationResponse.builder().status("ok").build();
    }
}
