package SD.ChatApp.controller;

import SD.ChatApp.dto.authentication.AuthenticationRequest;
import SD.ChatApp.dto.authentication.AuthenticationResponse;
import SD.ChatApp.dto.user.RegisterRequest;
import SD.ChatApp.dto.user.RegisterResponse;
import SD.ChatApp.model.User;
import SD.ChatApp.service.UserService;
import SD.ChatApp.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@Slf4j
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("avatar") MultipartFile avatar){
        RegisterRequest request = RegisterRequest.builder().
                name(name).
                username(username).
                password(password).
                avatar(avatar).
                build();
//        try {
            User user = userService.createUser(request);
            log.info("Created User: {}", user);
//            return ResponseEntity.status(HttpStatus.OK).body(RegisterResponse.builder().status("OK").build());
        /*
        Tam thoi de user trong response de kiem tra loi
         */
            return ResponseEntity.status(HttpStatus.OK).body(RegisterResponse.builder().user(user).build());
//        } catch (Exception e) {
//            log.info("Error: {}", e.toString());
////            ResponseEntity.status(HttpStatus.CONFLICT).body(RegisterResponse.builder().status("CONFLICT").build());
//        }
//        return null;

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


}
