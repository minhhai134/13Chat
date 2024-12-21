package SD.ChatApp.dto.conversation.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetConversationListResponse {
    private List<OneToOneConversationList> oneToOneList;
    private List<GroupConversationList> groupList;
}
