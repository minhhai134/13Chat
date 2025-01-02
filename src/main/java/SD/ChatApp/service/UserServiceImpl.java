package SD.ChatApp.service;

import SD.ChatApp.dto.user.*;
import SD.ChatApp.exception.user.NameExistedException;
import SD.ChatApp.exception.user.UserNameExistedException;
import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.model.User;
import SD.ChatApp.model.enums.Relationship_Type;
import SD.ChatApp.model.enums.Role;
import SD.ChatApp.repository.UserRepository;
import SD.ChatApp.service.file.UploadService;
import SD.ChatApp.service.network.BlockService;
import SD.ChatApp.service.network.FriendRequestService;
import SD.ChatApp.service.network.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BlockService blockService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private FriendRequestService friendRequestService;
    @Autowired
    private UploadService uploadService;

    public User createUser(RegisterRequest request) {

//        log.info("Request: {}", request);
        if(userRepository.findByUsername(request.getUsername()).isPresent())
            throw new UserNameExistedException();
        if(userRepository.findByName(request.getName()).isPresent())
            throw new NameExistedException();

        String avatar = uploadService.uploadFile(request.getAvatar());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User savedUser = userRepository.save(User.builder().
                                role(Role.USER).
                                username(request.getUsername()).
                                password(passwordEncoder.encode(request.getPassword())).
                                name(request.getName()).
                                avatar(avatar).
                                onlineStatus("on").
                                build());
//        log.info("Saved User: {}", savedUser);


        return savedUser;
    }

    public User login(LoginRequest request) {
        log.info("abc");
        User user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                    .orElseThrow(UserNotFoundException::new);
        log.info("User login: {}", user);
        return user;
    }

    public GetUserInfoResponse getUserInfo(Principal userPrincipal, String searchName){

        User user = userRepository.findByUsername(userPrincipal.getName()).orElseThrow();

        User foundUser = userRepository.findByName(searchName)
                .orElseThrow(UserNotFoundException::new);

        if(blockService.checkBlockstatus(user.getId(), foundUser.getId()) || blockService.checkBlockstatus(foundUser.getId(), user.getId()))
            throw new UserNotFoundException();

        GetUserInfoResponse response = GetUserInfoResponse.builder().
                userId(foundUser.getId()).
                name(foundUser.getName()).
                relationship(Relationship_Type.NONE).
                build();

        if(friendService.checkFriendRelationship(user.getId(), foundUser.getId())){
            response.setRelationship(Relationship_Type.FRIEND);
        }
        else if(friendRequestService.checkFriendRequestSent(user.getId(), foundUser.getId())){
            response.setRelationship(Relationship_Type.REQUEST_SENT);
        }
        else if(friendRequestService.checkFriendRequestSent(foundUser.getId(),user.getId())){
            response.setRelationship(Relationship_Type.REQUEST_RECEIVED);
        }

        return response;
    }

    public List<GetFriendRequestListResponse> getFriendRequestList(Principal userPrincipal) {
        User user = userRepository.findByUsername(userPrincipal.getName()).orElseThrow();
        List<GetFriendRequestListResponse> response = new ArrayList<>();
        try {
            log.info("UserID: {}", user.getId());
            response = userRepository.getFriendRequestList(user.getId());
            log.info("FriendRequest List: {}", response);
            return response;
        } catch (Exception e ) {
            // Handle JPA data access exception
            System.err.println("Error: " + e.getMessage());
            // You can also log the exception or rethrow it as a custom exception
            throw e;
        }

    }

    public List<GetFriendListResponse> getFriendList(Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        List<GetFriendListResponse> response = new ArrayList<>();
        try {
//            log.info("UserID: {}", userId);
            response = userRepository.getFriendList(user.getId());
//            log.info("FriendRequest List: {}", response);
            return response;
        } catch (NestedRuntimeException | HibernateException e ) {
            // Handle JPA data access exception
            System.err.println("Error: " + e.getMessage());
            // You can also log the exception or rethrow it as a custom exception
            throw e;
        }

    }

    public List<GetBlockListResponse> getBlockList(Principal userPrincipal) {
        User user = userRepository.findByUsername(userPrincipal.getName()).orElseThrow();
        List<GetBlockListResponse> response = new ArrayList<>();
        try {
            log.info("UserID: {}", user.getId());
            response = userRepository.getBlockList(user.getId());
//            log.info("FriendRequest List: {}", response);
            return response;
        } catch (NestedRuntimeException | HibernateException e ) {
            // Handle JPA data access exception
            System.err.println("Error: " + e.getMessage());
            // You can also log the exception or rethrow it as a custom exception
            throw e;
        }

    }


}
