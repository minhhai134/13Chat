package SD.ChatApp.controller;

import SD.ChatApp.dto.notification.GetNotificationListResponse;
import SD.ChatApp.dto.notification.SeenNotificationRequest;
import SD.ChatApp.dto.notification.SeenNotificationResponse;
import SD.ChatApp.model.notification.Notification;
import SD.ChatApp.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<GetNotificationListResponse> getNotifications(Principal principal){
        List<Notification> list = notificationService.getNotificationList(principal);
        return ResponseEntity.status(HttpStatus.OK).body(GetNotificationListResponse.builder().notificationList(list).build());
    }

    @PatchMapping("/seenNotification")
    public  ResponseEntity<SeenNotificationResponse> seenNotification(
            Principal principal,
            SeenNotificationRequest request){
        SeenNotificationResponse response = notificationService.seenNotification(principal, request);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
