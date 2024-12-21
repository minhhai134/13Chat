package SD.ChatApp.dto.conversation.group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGroupRequest {
    private String groupName;
    private String fixBug;
}
