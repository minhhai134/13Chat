package SD.ChatApp.dto.conversation;

import SD.ChatApp.model.conversation.Conversation;
import SD.ChatApp.model.conversation.GroupMetaData;
import SD.ChatApp.model.conversation.Membership;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGroupResponse {
    private Conversation conversation;
    private GroupMetaData metaData;
    private Membership adminMembership;
}
