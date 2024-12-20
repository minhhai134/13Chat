package SD.ChatApp.dto.conversation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGroupRequest {
    private String groupName;
}
