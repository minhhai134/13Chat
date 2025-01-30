package SD.ChatApp.repository;

import SD.ChatApp.dto.user.GetBlockListResponse;
import SD.ChatApp.dto.user.GetFriendListResponse;
import SD.ChatApp.dto.user.GetFriendRequestListResponse;
import SD.ChatApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String name);

    Optional<User> findByName(String name);

//    @Query(value = "select rq.id as requestId, u.id as senderId, u.name as senderName " +
//                   "from User u, FriendRequest rq where u.id = rq.senderId  " +
//                   "and rq.receiverId = :id")
    @Query(value = "select new SD.ChatApp.dto.user.GetFriendRequestListResponse(rq.id,u.id,u.name, u.avatar) " +
                   "from User u, FriendRequest rq where u.id = rq.senderId  " +
                   "and rq.receiverId = :id")
    List<GetFriendRequestListResponse> getFriendRequestList(@Param("id") String userId);

    @Query(value = "select new SD.ChatApp.dto.user.GetFriendListResponse(rl.id,u.id,u.name, u.avatar) " +
            "from User u, FriendRelation rl where u.id = rl.friendId  " +
            "and rl.userId = :id")
    List<GetFriendListResponse> getFriendList(@Param("id")String userId);

    @Query(value = "select new SD.ChatApp.dto.user.GetBlockListResponse(bl.id,u.id,u.name) " +
            "from User u, Block bl where u.id = bl.destinationUserId  " +
            "and bl.blockerUserId = :id")
    List<GetBlockListResponse> getBlockList(@Param("id")String userId);



}
