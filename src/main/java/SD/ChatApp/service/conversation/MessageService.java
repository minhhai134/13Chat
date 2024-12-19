package SD.ChatApp.service.conversation;

import SD.ChatApp.dto.message.ChatMessageReceiving;
import SD.ChatApp.dto.message.ChatMessageSending;
import SD.ChatApp.model.conversation.Message;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface MessageService {
     ChatMessageReceiving sendMessage(ChatMessageSending message, Principal userPrincipal);

     List<Message> getMessages(Principal principal, String conversationId, long pivotId);
}
