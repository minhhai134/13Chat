package SD.ChatApp.service;

import SD.ChatApp.dto.block.BlockRequest;
import SD.ChatApp.dto.block.UnblockRequest;
import SD.ChatApp.dto.friend.DeleteFriendRequestRequest;
import SD.ChatApp.dto.friend.FriendRequestRequest;
import SD.ChatApp.dto.friend.ResponseAddFriendRequest;
import SD.ChatApp.dto.friend.UnfriendRequest;
import SD.ChatApp.model.Block;
import SD.ChatApp.model.FriendRelation;
import SD.ChatApp.model.FriendRequest;
import org.springframework.stereotype.Service;

@Service
public interface FriendService {

    FriendRequest sendFriendRequest(FriendRequestRequest request);

    void responseFriendRequest(ResponseAddFriendRequest request);

    void deleteFriendRequest(DeleteFriendRequestRequest request);

    Block block(BlockRequest request);

    void unblock(UnblockRequest request);

    void unfriend(UnfriendRequest request);
}
