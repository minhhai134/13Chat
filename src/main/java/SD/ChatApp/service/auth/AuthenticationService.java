package SD.ChatApp.service.auth;

import SD.ChatApp.dto.authentication.AuthenticationRequest;
import SD.ChatApp.dto.authentication.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
