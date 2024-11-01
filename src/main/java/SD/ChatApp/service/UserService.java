package SD.ChatApp.service;

import SD.ChatApp.dto.user.LoginRequest;
import SD.ChatApp.dto.user.RegisterRequest;
import SD.ChatApp.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUser(RegisterRequest request);

    User login(LoginRequest request);
}
