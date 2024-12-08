package SD.ChatApp.controller;

import SD.ChatApp.dto.message.ChatMessage;
import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.model.User;
import SD.ChatApp.model.conversation.Message;
import SD.ChatApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@Slf4j
public class MessageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat")
    @SendToUser("/queue/messages")
    public Message sendMessage(ChatMessage input, Principal userPrincipal) throws JsonProcessingException {
        log.info("got input {}", input);


        // find the receiver user
        Optional<User> receiver = userRepository.findById(input.getReceiverId());
        if (!receiver.isPresent()) {
            throw new UserNotFoundException();
        }


        // create a chat message record
//        Message chatMessage = chatMessageRepository
//                .save(ChatMessage.builder().content(input.getContent()).receiver(input.getReceiver())
//                        .sender(userPrincipal.getId().toString()).timestamp(LocalDateTime.now()).build());
        Message chatMessage = Message.builder().build();
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


}
