package SD.ChatApp.service.network;

import SD.ChatApp.dto.block.BlockRequest;
import SD.ChatApp.dto.block.UnblockRequest;
import SD.ChatApp.dto.friend.DeleteFriendRequestRequest;
import SD.ChatApp.dto.friend.FriendRequestRequest;
import SD.ChatApp.dto.friend.ResponseAddFriendRequest;
import SD.ChatApp.dto.friend.UnfriendRequest;
import SD.ChatApp.model.network.Block;
import SD.ChatApp.model.network.FriendRequest;
import org.springframework.stereotype.Service;

@Service
public interface FriendService {

    public boolean checkFriendRelationship(String id1, String id2);


    FriendRequest sendFriendRequest(FriendRequestRequest request);

    void responseFriendRequest(ResponseAddFriendRequest request);

    void deleteFriendRequest(DeleteFriendRequestRequest request);

    Block block(BlockRequest request);

    void unblock(UnblockRequest request);

    void unfriend(UnfriendRequest request);


}
