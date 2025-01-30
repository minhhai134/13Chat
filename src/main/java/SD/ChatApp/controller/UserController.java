package SD.ChatApp.controller;

import SD.ChatApp.dto.block.BlockRequest;
import SD.ChatApp.dto.block.BlockResponse;
import SD.ChatApp.dto.block.UnblockRequest;
import SD.ChatApp.dto.block.UnblockResponse;
import SD.ChatApp.dto.friend.*;
import SD.ChatApp.dto.user.*;
import SD.ChatApp.model.User;
import SD.ChatApp.service.network.FriendRequestService;
import SD.ChatApp.service.network.FriendService;
import SD.ChatApp.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;


    @GetMapping("/principal")
    public ResponseEntity<Principal> getPrincipal(Principal principal){
        log.info("Principal: {}", principal);
        log.info("Auth: {}", SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok().body(principal);
    }

    //****************************************************************************************


    @GetMapping("/info/{searchName}")
    public ResponseEntity<GetUserInfoResponse> getUserInfo(
            Principal userPrincipal,
            @PathVariable String searchName){
        GetUserInfoResponse response = userService.getUserInfo(userPrincipal,searchName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/friend-request-list")
    public ResponseEntity<List<GetFriendRequestListResponse>> getFriendRequestList(
            Principal userPrincipal){
        List<GetFriendRequestListResponse> response = userService.getFriendRequestList(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/friend-list")
    public ResponseEntity<List<GetFriendListResponse>> getFriendList(Principal principal){
        List<GetFriendListResponse> response = userService.getFriendList(principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/block-list")
    public ResponseEntity<List<GetBlockListResponse>> getBlockList(Principal principal){
        List<GetBlockListResponse> response = userService.getBlockList(principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    //****************************************************************************************

    @PostMapping("/friend-request")
    public ResponseEntity<FriendRequestResponse> sendFriendRequest(
            Principal principal,
            @Valid @RequestBody FriendRequestRequest request){
        friendService.sendFriendRequest(principal, request);
        return ResponseEntity.status(HttpStatus.OK).body(FriendRequestResponse.builder().status("Request sent").build());
    }

    @PatchMapping("/friend-request")
    public ResponseEntity<RespondAddFriendResponse> responseFriendRequest(
            Principal principal,
            @Valid @RequestBody RespondAddFriendRequest request){
        friendService.respondFriendRequest(principal, request);
        return ResponseEntity.status(HttpStatus.OK).body(RespondAddFriendResponse.builder().status("ok").build());
    }

    @DeleteMapping("/friend-request")
    public ResponseEntity<DeleteFriendRequestResponse> deleteFriendRequest(
            Principal principal,
            @Valid @RequestBody DeleteFriendRequestRequest request){
        friendService.deleteFriendRequest(principal, request);

        return ResponseEntity.status(HttpStatus.OK).body(DeleteFriendRequestResponse.builder().status("ok").build());
    }

    @PostMapping("/block")
    public ResponseEntity<BlockResponse> block(
            Principal principal,
            @Valid @RequestBody BlockRequest request){
        friendService.block(principal, request);
        return ResponseEntity.status(HttpStatus.OK).body(BlockResponse.builder().status("ok").build());
    }

    @DeleteMapping("/unblock")
    public ResponseEntity<UnblockResponse> unblock(@Valid @RequestBody UnblockRequest request){
        friendService.unblock(request);
        return ResponseEntity.status(HttpStatus.OK).body(UnblockResponse.builder().status("ok").build());
    }

    @DeleteMapping("/friend")
    public ResponseEntity<UnfriendResponse> unblock(
            Principal principal,
            @Valid @RequestBody UnfriendRequest request){
        friendService.unfriend(principal, request);
        return ResponseEntity.status(HttpStatus.OK).body(UnfriendResponse.builder().status("ok").build());
    }

}
