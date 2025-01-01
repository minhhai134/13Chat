package SD.ChatApp.service.conversation;

import SD.ChatApp.dto.websocket.message.ChatMessageReceiving;
import SD.ChatApp.dto.websocket.message.ChatMessageSending;
import SD.ChatApp.model.conversation.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Service
public interface MessageService {
     ChatMessageReceiving sendMessage(
             Principal principal,
             ChatMessageSending input);

     List<Message> getMessages(
             Principal principal,
             String conversationId, long pivotId);

     ChatMessageReceiving sendFile(
             Principal principal,
             ChatMessageSending message,
             MultipartFile file);
}
