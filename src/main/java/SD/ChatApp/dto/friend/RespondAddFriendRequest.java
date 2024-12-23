package SD.ChatApp.dto.friend;

import SD.ChatApp.model.enums.FRIEND_REQUEST_RESPONSE;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespondAddFriendRequest {
    private String senderId;
    private FRIEND_REQUEST_RESPONSE response;
}
