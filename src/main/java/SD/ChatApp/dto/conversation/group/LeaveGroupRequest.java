package SD.ChatApp.dto.conversation.group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaveGroupRequest {
    private String memberId;
    private String groupId;
    private String membershipId;
}
