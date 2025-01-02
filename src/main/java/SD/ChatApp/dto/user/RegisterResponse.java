package SD.ChatApp.dto.user;

import SD.ChatApp.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private User user;
    private String status;
}
