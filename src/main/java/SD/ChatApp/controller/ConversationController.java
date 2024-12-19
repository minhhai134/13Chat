package SD.ChatApp.controller;

import SD.ChatApp.dto.conversation.*;
import SD.ChatApp.model.conversation.Membership;
import SD.ChatApp.model.enums.Membership_Status;
import SD.ChatApp.service.conversation.ConversationService;
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
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping("/new")
    public ResponseEntity<CreateOneToOneConversationResponse> createOneToOneConversation(
            Principal principal,
            @Valid @RequestBody CreateOneToOneConversationRequest request){
        CreateOneToOneConversationResponse response = conversationService.createOneToOneConversation(principal, request);
        return ResponseEntity.status(HttpStatus.OK).body(response) ;
    }

    @GetMapping
    public ResponseEntity<List<GetOneToOneConversationListResponse>> getConversationList(
            Principal principal,
            @RequestHeader Membership_Status membershipStatus){
        List<GetOneToOneConversationListResponse> response = conversationService.getConversationList(principal, membershipStatus);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/change_membership")
    public ResponseEntity<ChangeMembershipStatusResponse> changeMembershipStatus(
            Principal principal,
            @RequestBody ChangeMembershipStatusRequest request){
        Membership membership = conversationService.changeMembershipStatus(principal, request);
        return ResponseEntity.status(HttpStatus.OK).body(ChangeMembershipStatusResponse.builder().membership(membership).build());
    }






}
