package SD.ChatApp.dto.user;

import SD.ChatApp.enums.Relationship_Type;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserInfoResponse {
    private String userId;

    private String name;

    private String userAvt;

    private Relationship_Type relationship;
}
