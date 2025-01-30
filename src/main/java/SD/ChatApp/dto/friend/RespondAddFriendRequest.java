package SD.ChatApp.dto.friend;

import SD.ChatApp.enums.Friend_Request_Response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespondAddFriendRequest {
    private String senderId;
    private Friend_Request_Response response;
}
