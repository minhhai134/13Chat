package SD.ChatApp.service.network;

import SD.ChatApp.dto.block.BlockRequest;
import SD.ChatApp.dto.block.UnblockRequest;
import SD.ChatApp.dto.friend.DeleteFriendRequestRequest;
import SD.ChatApp.dto.friend.FriendRequestRequest;
import SD.ChatApp.dto.friend.RespondAddFriendRequest;
import SD.ChatApp.dto.friend.UnfriendRequest;
import SD.ChatApp.model.network.Block;
import SD.ChatApp.model.network.FriendRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface FriendService {

    boolean checkFriendRelationship(String id1, String id2);


    FriendRequest sendFriendRequest(Principal principal , FriendRequestRequest request);

    void respondFriendRequest(Principal principal, RespondAddFriendRequest request);

    void deleteFriendRequest(Principal principal, DeleteFriendRequestRequest request);

    Block block(Principal principal, BlockRequest request);

    void unblock(UnblockRequest request);

    void unfriend(Principal principal, UnfriendRequest request);


}
