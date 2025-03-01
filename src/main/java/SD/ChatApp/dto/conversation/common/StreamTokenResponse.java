package SD.ChatApp.dto.conversation.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StreamTokenResponse {
    private String streamUserToken;
}
