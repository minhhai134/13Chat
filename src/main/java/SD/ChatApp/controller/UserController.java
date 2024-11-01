package SD.ChatApp.controller;

import SD.ChatApp.dto.friend.FriendRequestRequest;
import SD.ChatApp.dto.friend.FriendRequestResponse;
import SD.ChatApp.dto.user.*;
import SD.ChatApp.model.User;
import SD.ChatApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(@Valid @RequestBody RegisterRequest request){

        User user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(RegisterResponse.builder().user(user).build());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        User user = userService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(LoginResponse.builder().user(user).build());
    }

    @GetMapping("{username}")
    public ResponseEntity<GetUserInfoResponse> getUserInfo(@Valid @RequestBody GetUserInfoRequest request){

        return ResponseEntity.status(HttpStatus.OK).body(GetUserInfoResponse.builder().build());
    }

    @PostMapping("/sendFriendRequest")
    public ResponseEntity<FriendRequestResponse> sendFriendRequest(@Valid @RequestBody FriendRequestRequest request){

        return ResponseEntity.status(HttpStatus.OK).body(FriendRequestResponse.builder().build());
    }







}
