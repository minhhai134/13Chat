package SD.ChatApp.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserInfoRequest {

    private String userId; // tam thoi

    private String searchName;
}
