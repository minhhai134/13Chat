package SD.ChatApp.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetFriendRequestListResponse {
    private String requestId;

    private String senderId;

    private String senderName;

    public GetFriendRequestListResponse(String requestId, String senderId, String senderName) {
        this.requestId = requestId;
        this.senderId = senderId;
        this.senderName = senderName;
    }
}
