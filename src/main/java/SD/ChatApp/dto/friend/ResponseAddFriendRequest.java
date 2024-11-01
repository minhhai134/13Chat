package SD.ChatApp.dto.friend;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseAddFriendRequest {
    private String requestId;

    private String receiverId;

    private String senderId;

    private String response;

}
