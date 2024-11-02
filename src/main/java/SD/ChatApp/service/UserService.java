package SD.ChatApp.service;

import SD.ChatApp.dto.user.*;
import SD.ChatApp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User createUser(RegisterRequest request);

    User login(LoginRequest request);

    GetUserInfoResponse getUserInfo(String userId, String searchName);

    List<GetFriendRequestListResponse> getFriendRequestList(String userId);

    List<GetFriendListResponse> getFriendList(String userId);

    List<GetBlockListResponse> getBlockList(String userId);


}
