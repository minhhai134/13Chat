package SD.ChatApp.service.auth;

import SD.ChatApp.helper.CookieHelper;
import SD.ChatApp.helper.EncryptionHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.SerializationUtils;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;

@Component
@Slf4j
public class CustomStatelessOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    private static final Duration OAUTH_COOKIE_EXPIRY = Duration.ofMinutes(5);
    private static final Base64.Encoder B64E = Base64.getEncoder();
    private static final Base64.Decoder B64D = Base64.getDecoder();

    private final SecretKey encryptionKey = EncryptionHelper.generateKey();
    private final String OAUTH_COOKIE_NAME = "Cookie-for-stateless-OAuth2.0";

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Assert.notNull(request, "request cannot be null");
        String stateParameter = getStateParameter(request);
        if (stateParameter == null) {
            return null;
        }
        log.info("Load auth request");
        OAuth2AuthorizationRequest auth2AuthorizationRequest = this.retrieveCookie(request);
        log.info("Load auth request: {}", auth2AuthorizationRequest.toString());
        return auth2AuthorizationRequest;
//        return  null;
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(response, "response cannot be null");

        if (authorizationRequest == null) {
            this.removeCookie(response);
            return;
        }

        String state = authorizationRequest.getState();
        Assert.hasText(state, "authorizationRequest.state cannot be empty");

        this.attachCookie(request, response, authorizationRequest);
        log.info("Save auth request: {}", state);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        OAuth2AuthorizationRequest auth2AuthorizationRequest = this.retrieveCookie(request);
        log.info("Remove auth request: {}", auth2AuthorizationRequest.getState());
        return this.retrieveCookie(request);
    }


    private OAuth2AuthorizationRequest retrieveCookie(HttpServletRequest request) {
        return CookieHelper.retrieve(request.getCookies(), OAUTH_COOKIE_NAME)
                .map(this::decrypt)
                .orElse(null);
    }

    private void attachCookie(HttpServletRequest request, HttpServletResponse response, OAuth2AuthorizationRequest value) {
        String cookie = CookieHelper.generateCookie(OAUTH_COOKIE_NAME, this.encrypt(value), OAUTH_COOKIE_EXPIRY, request);
        response.setHeader(HttpHeaders.SET_COOKIE, cookie);
    }

    private void removeCookie(HttpServletResponse response) {
        String expiredCookie = CookieHelper.generateExpiredCookie(OAUTH_COOKIE_NAME);
        response.setHeader(HttpHeaders.SET_COOKIE, expiredCookie);
    }

    private String encrypt(OAuth2AuthorizationRequest authorizationRequest) {
        byte[] bytes = SerializationUtils.serialize(authorizationRequest);
        byte[] encryptedBytes = EncryptionHelper.encrypt(this.encryptionKey, bytes);
        return B64E.encodeToString(encryptedBytes);
    }

    private OAuth2AuthorizationRequest decrypt(String encrypted) {
        byte[] encryptedBytes = B64D.decode(encrypted);
        byte[] bytes = EncryptionHelper.decrypt(this.encryptionKey, encryptedBytes);
        return (OAuth2AuthorizationRequest) SerializationUtils.deserialize(bytes);
    }

    private String getStateParameter(HttpServletRequest request) {
        return request.getParameter(OAuth2ParameterNames.STATE);
    }
}
