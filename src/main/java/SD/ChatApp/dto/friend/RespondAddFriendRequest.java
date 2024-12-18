package SD.ChatApp.dto.friend;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespondAddFriendRequest {
    private String requestId;

    private String senderId;

    private String response;

}
