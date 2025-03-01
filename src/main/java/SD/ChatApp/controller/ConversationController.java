package SD.ChatApp.controller;

import SD.ChatApp.dto.conversation.common.*;
import SD.ChatApp.dto.conversation.group.*;
import SD.ChatApp.dto.conversation.onetoone.CreateOneToOneConversationRequest;
import SD.ChatApp.dto.conversation.onetoone.CreateOneToOneConversationResponse;
import SD.ChatApp.model.conversation.Membership;
import SD.ChatApp.enums.Membership_Status;
import SD.ChatApp.service.conversation.ConversationService;
import SD.ChatApp.service.videocall.VideoCallService;
import io.getstream.exceptions.StreamException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/conversation")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ConversationController {

    private final ConversationService conversationService;
    private final VideoCallService videoCallService;

    @PostMapping("/new")
    public ResponseEntity<CreateOneToOneConversationResponse> createOneToOneConversation(
            Principal principal,
            @Valid @RequestBody CreateOneToOneConversationRequest request){
        CreateOneToOneConversationResponse response = conversationService.createOneToOneConversation(principal, request);
        return ResponseEntity.status(HttpStatus.OK).body(response) ;
    }

    @GetMapping
    public ResponseEntity<GetConversationListResponse> getConversationList(
            Principal principal,
            @RequestHeader Membership_Status membershipStatus){
        GetConversationListResponse response = conversationService.getConversationList(principal, membershipStatus);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/change_membership")
    public ResponseEntity<ChangeMembershipStatusResponse> changeMembershipStatus(
            Principal principal,
            @Valid @RequestBody ChangeMembershipStatusRequest request){
        Membership membership = conversationService.changeMembershipStatus(principal, request);
        return ResponseEntity.status(HttpStatus.OK).body(ChangeMembershipStatusResponse.builder().membership(membership).build());
    }

    @PostMapping("/create_group")
    public ResponseEntity<CreateGroupResponse> createGroup(
            Principal principal,
            @Valid @RequestBody CreateGroupRequest request
            ){
        log.info("{}", request);
        CreateGroupResponse response = conversationService.createGroupConversation(principal, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
//        return null;

    }

    @PostMapping("/add_member")
    public ResponseEntity<AddMemberResponse> addMember(
            Principal principal,
            @Valid @RequestBody AddMemberRequest request){
        AddMemberResponse response = conversationService.addMember(principal, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/leave_group")
    public ResponseEntity<LeaveGroupResponse> leaveGroup(
            Principal principal,
            @Valid @RequestBody LeaveGroupRequest request){
        LeaveGroupResponse response = conversationService.leaveGroup(principal, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete_member")
    public ResponseEntity<DeleteMemberResponse> deleteMember(
            Principal principal,
            @Valid @RequestBody DeleteMemberRequest request){
        DeleteMemberResponse response = conversationService.deleteMember(principal, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/member_list")
    public ResponseEntity<List<GetGroupMemberResponse>> getMemberList(
            Principal principal,
            @RequestHeader String conversationId){
        List<GetGroupMemberResponse> response = conversationService.getMemberList(principal, conversationId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/video-call")
    public ResponseEntity<VideoCallResponse> makeVideoCall(
            Principal principal,
            @Valid @RequestBody VideoCallRequest request) throws StreamException {
        String callId = videoCallService.createCall(principal, request.getConversationId());
        return ResponseEntity.status(HttpStatus.OK).body(VideoCallResponse.builder().callId(callId).build());
    }






}
