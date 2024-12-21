package SD.ChatApp.dto.conversation.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetConversationListResponse {
    private List<OneToOneConversationDto> oneToOneList;
    private List<GroupConversationDto> groupList;
}
