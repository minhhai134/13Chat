package SD.ChatApp.dto.friend;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteFriendRequestRequest {
    private String receiverId;
    private String fixBug;
}
