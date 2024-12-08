package SD.ChatApp.service.network;

import SD.ChatApp.model.network.FriendRequest;
import org.springframework.stereotype.Service;

@Service
public interface FriendRequestService {

    boolean checkFriendRequestSent(String senderId, String receiverId);

    FriendRequest saveFriendRequest(String senderId, String receiverId);

    void deleteFriendRequest(String requestId);
}
