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

import java.util.List;

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

    //****************************************************************************************

    @GetMapping("/info/{searchName}")
    public ResponseEntity<GetUserInfoResponse> getUserInfo(@RequestHeader("id") String userId,@PathVariable String searchName){
        GetUserInfoResponse response = userService.getUserInfo(userId,searchName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/friend-request-list")
    public ResponseEntity<List<GetFriendRequestListResponse>> getFriendRequestList(@RequestHeader("id") String userId){
        List<GetFriendRequestListResponse> response = userService.getFriendRequestList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/friend-list")
    public ResponseEntity<List<GetFriendListResponse>> getFriendList(@RequestHeader("id") String userId){
        List<GetFriendListResponse> response = userService.getFriendList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/block-list")
    public ResponseEntity<List<GetBlockListResponse>> getBlockList(@RequestHeader("id") String userId){
        List<GetBlockListResponse> response = userService.getBlockList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    //****************************************************************************************

    @PostMapping("/friend-request")
    public ResponseEntity<FriendRequestResponse> sendFriendRequest(@Valid @RequestBody FriendRequestRequest request){
        friendService.sendFriendRequest(request);
        return ResponseEntity.status(HttpStatus.OK).body(FriendRequestResponse.builder().status("Request sent").build());
    }

    @PatchMapping("/friend-request")
    public ResponseEntity<ResponseAddFriendResponse> responseFriendRequest(@Valid @RequestBody ResponseAddFriendRequest request){
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
