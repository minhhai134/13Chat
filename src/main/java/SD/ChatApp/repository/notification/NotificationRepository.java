package SD.ChatApp.repository.notification;

import SD.ChatApp.model.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,String> {

    @Query("select nt from Notification nt where nt.userId = :userId order by nt.id desc LIMIT 30")
    List<Notification> getNotificationList(String userId);

}
