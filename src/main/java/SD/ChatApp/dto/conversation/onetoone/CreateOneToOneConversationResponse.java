package SD.ChatApp.dto.conversation.onetoone;

import SD.ChatApp.model.conversation.Conversation;
import SD.ChatApp.model.conversation.Membership;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOneToOneConversationResponse {
    private Conversation conversation;
    private Membership membership;
}
