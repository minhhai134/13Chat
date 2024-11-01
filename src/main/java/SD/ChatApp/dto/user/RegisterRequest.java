package SD.ChatApp.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {

    private String name;

    private String username;

    private String password;
}
