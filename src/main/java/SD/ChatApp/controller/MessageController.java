package SD.ChatApp.controller;

import SD.ChatApp.dto.message.UploadFileMessageResponse;
import SD.ChatApp.dto.websocket.message.ChatMessageReceiving;
import SD.ChatApp.dto.websocket.message.ChatMessageSending;
import SD.ChatApp.dto.message.GetMessagesResponse;
import SD.ChatApp.exception.user.UserNameExistedException;
import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.model.User;
import SD.ChatApp.model.conversation.Message;
import SD.ChatApp.model.enums.Conversation_Type;
import SD.ChatApp.model.enums.Membership_Status;
import SD.ChatApp.model.enums.Message_Type;
import SD.ChatApp.repository.UserRepository;
import SD.ChatApp.service.conversation.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class MessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    @MessageMapping("/one_to_one_chat")
    @SendToUser("/queue/messages")
    public ChatMessageReceiving sendOneToOneMessage(Principal principal, ChatMessageSending input) throws JsonProcessingException {
        log.info("got input {}", input);

        ChatMessageReceiving chatMessage = messageService.sendMessage(principal, input);
        if(chatMessage==null) return null;

        // send chat message to topic exchange
        String routingKey = "chat.private." + input.getDestinationId();

        User receiver = userRepository.findById(input.getDestinationId()).orElseThrow(UserNotFoundException::new);
        messagingTemplate.convertAndSendToUser(
                receiver.getUsername(), "/queue/messages", chatMessage);
        // rabbitTemplate.convertAndSend(MessageQueueConfig.CHAT_EXCHANGE, routingKey,
        // objectMapper.writeValueAsString(chatMessage));
//        log.info("sent message to chat exchange = {}, routing Key = {}, message = {}",
//                MessageQueueConfig.CHAT_EXCHANGE,
//                routingKey, chatMessage);
        return chatMessage;
    }

    @MessageMapping("/group_chat")
    public ChatMessageReceiving sendGroupMessage(Principal principal, ChatMessageSending input) throws JsonProcessingException{
        ChatMessageReceiving chatMessage = messageService.sendMessage(principal, input);
        if(chatMessage==null) return null;

        messagingTemplate.convertAndSend("/topic/"+input.getDestinationId(), chatMessage);
        return chatMessage;
    }

//    @GetMapping("{conversationId}")
    @GetMapping
    public ResponseEntity<GetMessagesResponse> getMessages(
            Principal principal,
            @RequestParam String conversationId,
            @RequestParam long pivotId){

        List<Message> list = messageService.getMessages(principal, conversationId, pivotId);
        return ResponseEntity.status(HttpStatus.OK).body(GetMessagesResponse.builder().messages(list).build());
    }


    @PostMapping("/files")
    public ResponseEntity<ChatMessageReceiving> sendFile(
            Principal principal,
            @RequestPart("file") MultipartFile file,
            @RequestHeader("conversationId") String conversationId,
            @RequestHeader("destinationId") String destinationId,
            @RequestHeader("sentTime") Instant sentTime,
            @RequestHeader("membershipStatus") Membership_Status membershipStatus,
            @RequestHeader("conversationType") Conversation_Type conversationType
            ){

        ChatMessageSending message = ChatMessageSending.builder()
                .conversationId(conversationId)
                .destinationId(destinationId)
                .sentTime(sentTime)
                .membershipStatus(membershipStatus)
                .messageType(Message_Type.FILE_MESSAGE)
                .conversationType(conversationType)
                .build();

        ChatMessageReceiving chatMessage = messageService.sendFile(principal, message, file);
        if(chatMessage==null) return null;

        if(message.getConversationType()==Conversation_Type.OneToOne){
            User receiver = userRepository.findById(destinationId).orElseThrow(UserNotFoundException::new);
            messagingTemplate.convertAndSendToUser(
                    receiver.getUsername(), "/queue/messages", chatMessage);
        }
        else if(message.getConversationType()==Conversation_Type.Group){
            messagingTemplate.convertAndSend("/topic/"+destinationId, chatMessage);
        }

        return ResponseEntity.status(HttpStatus.OK).body(chatMessage);
    }

}
