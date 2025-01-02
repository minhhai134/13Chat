package SD.ChatApp.dto.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class RegisterRequest {

    private String name;

    private String username;

    private String password;

    private MultipartFile avatar;
}
