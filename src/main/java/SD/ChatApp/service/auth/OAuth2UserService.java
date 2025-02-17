package SD.ChatApp.service.auth;

import SD.ChatApp.dto.authentication.Oauth2UserInfoDto;
import SD.ChatApp.dto.authentication.UserPrincipal;
import SD.ChatApp.enums.Role;
import SD.ChatApp.model.User;
import SD.ChatApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final String GOOGLE_AUTH_PROVIDER = "google";
    private final String GITHUB_AUTH_PROVIDER = "github";

    @Override
    @SneakyThrows
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        log.trace("Load user {}", oAuth2UserRequest);
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        Oauth2UserInfoDto userInfoDto = Oauth2UserInfoDto.builder().build();

        if(oAuth2UserRequest.getClientRegistration().getRegistrationId().toString().equals(GOOGLE_AUTH_PROVIDER)) {
            userInfoDto.setName(oAuth2User.getAttributes().get("name").toString());
            userInfoDto.setId(oAuth2User.getAttributes().get("sub").toString());
            userInfoDto.setUsername(oAuth2User.getAttributes().get("email").toString());
            userInfoDto.setAvatar(oAuth2User.getAttributes().get("picture").toString());
        }
        else if(oAuth2UserRequest.getClientRegistration().getRegistrationId().toString().equals(GITHUB_AUTH_PROVIDER)){
            userInfoDto.setName(oAuth2User.getAttributes().get("name").toString());
            userInfoDto.setId(oAuth2User.getAttributes().get("id").toString());
            userInfoDto.setUsername(oAuth2User.getAttributes().get("login").toString());
            userInfoDto.setAvatar(oAuth2User.getAttributes().get("avatar_url").toString());

        }
        log.info("UserInfoDto: {}", userInfoDto);
        Optional<User> userOptional = userRepository.findByUsername(userInfoDto.getUsername());
        User user = userOptional
                .map(existingUser -> updateExistingUser(existingUser, userInfoDto))
                .orElseGet(() -> registerNewUser(oAuth2UserRequest, userInfoDto));
        log.info("User's info: {}", user);
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, Oauth2UserInfoDto userInfoDto) {
        User user = new User();
        user.setAuthProvider(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        user.setProviderId(userInfoDto.getId());
        user.setName(userInfoDto.getName());
        user.setUsername(userInfoDto.getUsername());
        user.setAvatar(userInfoDto.getAvatar());
        user.setRole(Role.USER);
//        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, Oauth2UserInfoDto userInfoDto) {
        existingUser.setName(userInfoDto.getName());
        existingUser.setAvatar(userInfoDto.getAvatar());
        log.info("Update user: {}", existingUser);
        return userRepository.save(existingUser);
    }
}
