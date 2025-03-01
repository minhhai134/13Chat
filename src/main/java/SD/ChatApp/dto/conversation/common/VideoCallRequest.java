package SD.ChatApp.dto.conversation.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoCallRequest {
    private String conversationId;
    private String fixBug;
}
