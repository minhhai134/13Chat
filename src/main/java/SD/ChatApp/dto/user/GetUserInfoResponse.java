package SD.ChatApp.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserInfoResponse {
    private String userId;

    private String relationship;
}
