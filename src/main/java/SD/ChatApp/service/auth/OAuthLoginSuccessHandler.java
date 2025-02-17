package SD.ChatApp.service.auth;

import SD.ChatApp.dto.authentication.UserPrincipal;
import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.model.User;
import SD.ChatApp.repository.UserRepository;
import io.minio.credentials.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    private final String REDIRECT_URL = "http://localhost:5173/oauth-login-success?token=";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        log.info("Success Handler: {}", oAuth2User);

        // Generate JWT token
        User user = userRepository.findByUsername(((UserPrincipal) authentication.getPrincipal()).getUsername()).orElseThrow(UserNotFoundException::new);
        log.info("Success Handler: {}", user);
        String token = jwtService.generateToken(user);

        // Redirect to front-end with token
//        response.sendRedirect(REDIRECT_URL + token);
        response.sendRedirect("http://localhost:5173/login");



    }
}
