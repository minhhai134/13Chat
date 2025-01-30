package SD.ChatApp.service.network;

import SD.ChatApp.dto.block.BlockRequest;
import SD.ChatApp.dto.block.UnblockRequest;
import SD.ChatApp.dto.friend.DeleteFriendRequestRequest;
import SD.ChatApp.dto.friend.FriendRequestRequest;
import SD.ChatApp.dto.friend.RespondAddFriendRequest;
import SD.ChatApp.dto.friend.UnfriendRequest;
import SD.ChatApp.exception.friend.FriendRelationshipExistedException;
import SD.ChatApp.exception.friend.FriendRelationshipNotFound;
import SD.ChatApp.exception.friend.FriendRequestExistedException;
import SD.ChatApp.exception.request.InvalidRequestException;
import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.model.User;
import SD.ChatApp.enums.Friend_Request_Response;
import SD.ChatApp.enums.Notification_Type;
import SD.ChatApp.model.network.Block;
import SD.ChatApp.model.network.FriendRelation;
import SD.ChatApp.model.network.FriendRequest;
import SD.ChatApp.model.notification.Notification;
import SD.ChatApp.repository.UserRepository;
import SD.ChatApp.repository.network.FriendRelationRepository;
import SD.ChatApp.service.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRelationRepository friendRelationRepository;
    @Autowired
    private BlockService blockService;
    @Autowired
    private FriendRequestService friendRequestService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  SimpMessagingTemplate messagingTemplate;
    @Autowired
    private NotificationService notificationService;



    public boolean checkFriendRelationship(String id1, String id2) {
        if(friendRelationRepository.findByUserIdAndFriendId(id1,id2).isPresent())
            return true;
        else return false;
    }

    private FriendRelation addFriend(String senderId, String receiverId){
        friendRelationRepository.save(FriendRelation.builder().userId(senderId).friendId(receiverId).build());
        return friendRelationRepository.save(FriendRelation.builder().userId(receiverId).friendId(senderId).build());

    }

    private void deleteFriendRelationship(String userId, String friendId){
        if(checkFriendRelationship(userId,friendId)){
            friendRelationRepository.deleteByUserIdAndFriendId(userId,friendId);
            friendRelationRepository.deleteByUserIdAndFriendId(friendId,userId);
        }

    }


    public FriendRequest sendFriendRequest(Principal principal, FriendRequestRequest request) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        String senderId = user.getId();
        String receiverId = request.getReceiverId();
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(UserNotFoundException::new);

        if(blockService.checkBlockstatus(senderId, receiverId) || blockService.checkBlockstatus(receiverId, senderId))
            throw new UserNotFoundException();

        if(checkFriendRelationship(senderId,receiverId))
            throw new FriendRelationshipExistedException();

        if(friendRequestService.checkFriendRequestSent(senderId, receiverId) || friendRequestService.checkFriendRequestSent(receiverId, senderId))
            throw new FriendRequestExistedException();

        FriendRequest savedRequest = friendRequestService.saveFriendRequest(senderId,receiverId);

        Notification notification = Notification.builder().
                notificationType(Notification_Type.RECEIVED_FRIEND_REQUEST).
                userId(receiver.getId()).
                seenStatus(false).
                notificationContent(String.format("%s đã gửi lời mời kết bạn", user.getUsername())).
                build();
        notificationService.sendNotification(notification, receiver);
//        messagingTemplate.convertAndSend("/topic/"+receiver.getId(), notification);

        return savedRequest;
    }

    public void respondFriendRequest(Principal principal, RespondAddFriendRequest request){

        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        String receiverId = user.getId();
        String senderId = request.getSenderId();
        User sender = userRepository.findById(senderId).orElseThrow(UserNotFoundException::new);
        Friend_Request_Response response = request.getResponse();

        // Handle exceptions:

        if(response== Friend_Request_Response.ACCEPT){
            friendRequestService.deleteFriendRequest(senderId,receiverId);
            addFriend(senderId,receiverId);

            Notification notification = Notification.builder().
                    notificationType(Notification_Type.FRIEND_REQUEST_ACCEPTED).
                    userId(sender.getId()).
                    seenStatus(false).
                    notificationContent(String.format("%s đã chấp nhận lời mời kết bạn", sender.getUsername())).
                    build();
            notificationService.sendNotification(notification, sender);
//            return addFriend(senderId,receiverId);
        }
        else if(response== Friend_Request_Response.REJECT){
            friendRequestService.deleteFriendRequest(senderId,receiverId);
//            return null;
        }
        else throw new InvalidRequestException();
    }

    public void deleteFriendRequest(Principal principal, DeleteFriendRequestRequest request){
        // Handle exceptions:
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        User receiver = userRepository.findById(request.getReceiverId()).orElseThrow(UserNotFoundException::new);
        friendRequestService.deleteFriendRequest(user.getId(), receiver.getId());
    }

    /*
    Thua phuong thuc block, trong BlockService cung co??
     */
    public Block block(Principal principal, BlockRequest request){
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        String blockerUserId = user.getId();

        String destinationUserId = request.getDestinationUserId();

        // Handle exceptions:

        // UNFRIEND:
        deleteFriendRelationship(blockerUserId, destinationUserId);

        return blockService.block(blockerUserId,destinationUserId);
    }

    public void unblock(UnblockRequest request) {
         String blockId = request.getBlockId();
         String destinationUserId = request.getDestinationUserId();

         // Handle exception

        blockService.unBlock(blockId);

    }

    public void unfriend(Principal principal, UnfriendRequest request) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        User friend = userRepository.findById(request.getFriendId()).orElseThrow(UserNotFoundException::new);

        String userId = user.getId();
        String friendId = friend.getId();

        // Handle exceptions:
        //...

        if(checkFriendRelationship(userId,friendId)) {
            log.info("friend:true");

            try {
                friendRelationRepository.deleteByUserIdAndFriendId(userId, friendId);
                friendRelationRepository.deleteByUserIdAndFriendId(friendId, userId);
            } catch (DataAccessException e) {
                // Handle JPA data access exception
                System.err.println("Error deleting relationship: " + e.getMessage());
                // You can also log the exception or rethrow it as a custom exception
            }
        }
        else throw new FriendRelationshipNotFound();
    }



}

