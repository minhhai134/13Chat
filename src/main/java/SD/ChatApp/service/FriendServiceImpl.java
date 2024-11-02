package SD.ChatApp.service;

import SD.ChatApp.dto.block.BlockRequest;
import SD.ChatApp.dto.block.UnblockRequest;
import SD.ChatApp.dto.friend.DeleteFriendRequestRequest;
import SD.ChatApp.dto.friend.FriendRequestRequest;
import SD.ChatApp.dto.friend.ResponseAddFriendRequest;
import SD.ChatApp.dto.friend.UnfriendRequest;
import SD.ChatApp.exception.friend.FriendRelationshipExistedException;
import SD.ChatApp.exception.friend.FriendRelationshipNotFound;
import SD.ChatApp.exception.friend.FriendRequestExistedException;
import SD.ChatApp.exception.request.InvalidRequestException;
import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.model.Block;
import SD.ChatApp.model.FriendRelation;
import SD.ChatApp.model.FriendRequest;
import SD.ChatApp.repository.FriendRelationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendRelationRepository friendRelationRepository;
    @Autowired
    private BlockService blockService;
    @Autowired
    private FriendRequestService friendRequestService;


    public boolean checkFriendRelationship(String id1, String id2) {
        if(friendRelationRepository.findByUserIdAndFriendId(id1,id2).isPresent())
            return true;
        else return false;
    }

    public FriendRelation addFriend(String senderId, String receiverId){
        friendRelationRepository.save(FriendRelation.builder().userId(senderId).friendId(receiverId).build());
        return friendRelationRepository.save(FriendRelation.builder().userId(receiverId).friendId(senderId).build());

    }

    public void deleteFriendRelationship(String userId, String friendId){
        if(checkFriendRelationship(userId,friendId)){
            friendRelationRepository.deleteByUserIdAndFriendId(userId,friendId);
            friendRelationRepository.deleteByUserIdAndFriendId(friendId,userId);
        }

    }


    public FriendRequest sendFriendRequest(FriendRequestRequest request) {
        String senderId = request.getSenderId();
        String receiverId = request.getReceiverId();

        if(blockService.checkBlockstatus(senderId, receiverId) || blockService.checkBlockstatus(receiverId, senderId))
            throw new UserNotFoundException();

        if(checkFriendRelationship(senderId,receiverId))
            throw new FriendRelationshipExistedException();

        if(friendRequestService.checkFriendRequestSent(senderId, receiverId) || friendRequestService.checkFriendRequestSent(receiverId, senderId))
            throw new FriendRequestExistedException();

        FriendRequest savedRequest = friendRequestService.saveFriendRequest(senderId,receiverId);

        return savedRequest;
    }

    public void responseFriendRequest(ResponseAddFriendRequest request){
        String requestId = request.getRequestId();
        String receiverId = request.getReceiverId();
        String senderId = request.getSenderId();
        String response = request.getResponse();

        // Handle exceptions:

        if(response.equals("accept")){
            friendRequestService.deleteFriendRequest(requestId);
            addFriend(senderId,receiverId);
//            return addFriend(senderId,receiverId);
        }
        else if(response.equals("reject")){
            friendRequestService.deleteFriendRequest(requestId);
//            return null;
        }
        else throw new InvalidRequestException();
    }

    public void deleteFriendRequest(DeleteFriendRequestRequest request){

        // Handle exceptions:

        friendRequestService.deleteFriendRequest(request.getRequestId());
    }

    public Block block(BlockRequest request){
        String blockerUserId = request.getBlockerUserId();
        String destinationUserId = request.getDestinationUserId();

        // Handle exceptions:

        // UNFRIEND:
        deleteFriendRelationship(blockerUserId, destinationUserId);

        return blockService.block(blockerUserId,destinationUserId);
    }

    public void unblock(UnblockRequest request) {
         String blockId = request.getBlockId();
         String blockerUserId = request.getBlockerUserId();
         String destinationUserId = request.getDestinationUserId();

         // Handle exception

        blockService.unBlock(blockId);

    }

    public void unfriend(UnfriendRequest request) {
        String relationshipId = request.getRelationshipId();
        String userId = request.getUserId();
        String friendId = request.getFriendId();

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

