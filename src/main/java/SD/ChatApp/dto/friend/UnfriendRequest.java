package SD.ChatApp.dto.friend;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnfriendRequest {
    private String friendId;
    private String fixBug;
}
