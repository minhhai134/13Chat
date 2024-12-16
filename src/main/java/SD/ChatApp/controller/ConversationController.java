package SD.ChatApp.controller;

import SD.ChatApp.dto.conversation.GetConversationListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conversation")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
@Slf4j
public class ConversationController {

    @GetMapping
    public ResponseEntity<GetConversationListResponse> getConversationList(){
        


        return null;
    }



}
