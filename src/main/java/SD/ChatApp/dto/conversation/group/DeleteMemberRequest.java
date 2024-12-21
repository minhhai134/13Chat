package SD.ChatApp.dto.conversation.group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteMemberRequest {
    private String memberId;
    private String groupId;
}
