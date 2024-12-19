package SD.ChatApp.controller;

import SD.ChatApp.dto.message.ChatMessageReceiving;
import SD.ChatApp.dto.message.ChatMessageSending;
import SD.ChatApp.dto.message.GetMessagesResponse;
import SD.ChatApp.model.conversation.Message;
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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    @SendToUser("/queue/messages")
    public ChatMessageReceiving sendMessage(ChatMessageSending input, Principal userPrincipal) throws JsonProcessingException {
        log.info("got input {}", input);


        ChatMessageReceiving chatMessage = messageService.sendMessage(input, userPrincipal);
        // send chat message to topic exchange
        String routingKey = "chat.private." + input.getReceiverId();
        messagingTemplate.convertAndSendToUser(
                input.getReceiverId(), "/queue/messages", input);
        // rabbitTemplate.convertAndSend(MessageQueueConfig.CHAT_EXCHANGE, routingKey,
        // objectMapper.writeValueAsString(chatMessage));
//        log.info("sent message to chat exchange = {}, routing Key = {}, message = {}",
//                MessageQueueConfig.CHAT_EXCHANGE,
//                routingKey, chatMessage);
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


}
