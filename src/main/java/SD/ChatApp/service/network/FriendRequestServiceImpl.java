package SD.ChatApp.service.network;

import SD.ChatApp.model.network.FriendRequest;
import SD.ChatApp.repository.network.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    public boolean checkFriendRequestSent(String senderId, String receiverId) {
        if(friendRequestRepository.findBySenderIdAndReceiverId(senderId,receiverId).isPresent())
            return true;
        else return false;
    }

    public FriendRequest saveFriendRequest(String senderId, String receiverId){
        return friendRequestRepository.save(FriendRequest.builder().senderId(senderId).receiverId(receiverId).build());
    }

    public void deleteFriendRequest(String requestId) {
         friendRequestRepository.deleteById(requestId);
    }

}



