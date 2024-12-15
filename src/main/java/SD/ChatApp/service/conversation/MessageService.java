package SD.ChatApp.service.conversation;

import SD.ChatApp.dto.message.ChatMessageReceiving;
import SD.ChatApp.dto.message.ChatMessageSending;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface MessageService {
    public ChatMessageReceiving sendMessage(ChatMessageSending message, Principal userPrincipal);
}
