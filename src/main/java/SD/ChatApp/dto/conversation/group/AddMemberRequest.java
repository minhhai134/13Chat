package SD.ChatApp.dto.conversation.group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddMemberRequest {
    private String conversationId;
    private String memberId;
    private String groupName;
}
