package SD.ChatApp.controller;

import SD.ChatApp.dto.block.BlockRequest;
import SD.ChatApp.dto.block.BlockResponse;
import SD.ChatApp.dto.block.UnblockRequest;
import SD.ChatApp.dto.block.UnblockResponse;
import SD.ChatApp.dto.friend.*;
import SD.ChatApp.dto.user.*;
import SD.ChatApp.model.User;
import SD.ChatApp.service.FriendService;
import SD.ChatApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;

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

    @PostMapping("/friend-request")
    public ResponseEntity<FriendRequestResponse> sendFriendRequest(@Valid @RequestBody FriendRequestRequest request){
        friendService.sendFriendRequest(request);
        return ResponseEntity.status(HttpStatus.OK).body(FriendRequestResponse.builder().status("Request sent").build());
    }

    @PatchMapping("/friend-request")
    public ResponseEntity<ResponseAddFriendResponse> responseFriendReuqest(@Valid @RequestBody ResponseAddFriendRequest request){
        friendService.responseFriendRequest(request);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseAddFriendResponse.builder().status("ok").build());
    }

    @DeleteMapping("/friend-request")
    public ResponseEntity<DeleteFriendRequestResponse> deleteFriendRequest(@Valid @RequestBody DeleteFriendRequestRequest request){
        friendService.deleteFriendRequest(request);

        return ResponseEntity.status(HttpStatus.OK).body(DeleteFriendRequestResponse.builder().status("ok").build());
    }

    @PostMapping("/block")
    public ResponseEntity<BlockResponse> block(@Valid @RequestBody BlockRequest request){
        friendService.block(request);
        return ResponseEntity.status(HttpStatus.OK).body(BlockResponse.builder().status("ok").build());
    }

    @DeleteMapping("/unblock")
    public ResponseEntity<UnblockResponse> unblock(@Valid @RequestBody UnblockRequest request){
        friendService.unblock(request);
        return ResponseEntity.status(HttpStatus.OK).body(UnblockResponse.builder().status("ok").build());
    }

    @DeleteMapping("/friend")
    public ResponseEntity<UnfriendResponse> unblock(@Valid @RequestBody UnfriendRequest request){
        friendService.unfriend(request);
        return ResponseEntity.status(HttpStatus.OK).body(UnfriendResponse.builder().status("ok").build());
    }






}
