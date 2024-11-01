package SD.ChatApp.service;

import SD.ChatApp.model.FriendRequest;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

@Service
public interface FriendRequestService {

    boolean checkFriendRequestSent(String senderId, String receiverId);

    FriendRequest saveFriendRequest(String senderId, String receiverId);

    void deleteFriendRequest(String requestId);
}
