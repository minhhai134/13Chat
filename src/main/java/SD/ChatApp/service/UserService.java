package SD.ChatApp.service;

import SD.ChatApp.dto.user.*;
import SD.ChatApp.model.User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface UserService {
    User createUser(RegisterRequest request);

    User login(LoginRequest request);

    GetUserInfoResponse getUserInfo(Principal userPrincipal, String searchName);

    List<GetFriendRequestListResponse> getFriendRequestList(Principal userPrincipal);

    List<GetFriendListResponse> getFriendList(Principal userPrincipal);

    List<GetBlockListResponse> getBlockList(Principal userPrincipal);


}
