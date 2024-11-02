package SD.ChatApp.service;

import SD.ChatApp.dto.user.*;
import SD.ChatApp.exception.user.NameExistedException;
import SD.ChatApp.exception.user.UserNameExistedException;
import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.model.User;
import SD.ChatApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.query.sqm.UnknownEntityException;
import org.hibernate.tool.schema.spi.SchemaManagementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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

    public User createUser(RegisterRequest request) {

        if(userRepository.findByUsername(request.getUsername()).isPresent())
            throw new UserNameExistedException();
        if(userRepository.findByName(request.getName()).isPresent())
            throw new NameExistedException();

        User savedUser = userRepository.save(User.builder().
                                username(request.getUsername()).
                                password(request.getPassword()).
                                name(request.getName()).
                                onlineStatus("on").
                                build());

        return savedUser;
    }

    public User login(LoginRequest request) {
        return userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() -> new UserNotFoundException());
    }

    public GetUserInfoResponse getUserInfo(String userId, String searchName){

        StringBuilder relationship = new StringBuilder("non-relationship");

        User foundUser = userRepository.findByName(searchName)
                .orElseThrow(() -> new UserNotFoundException());

        if(blockService.checkBlockstatus(userId, foundUser.getId()) || blockService.checkBlockstatus(foundUser.getId(), userId))
            throw new UserNotFoundException();

        if(friendService.checkFriendRelationship(userId, foundUser.getId())){
            relationship.setLength(0);
            relationship.append("friend");
        }
        else if(friendRequestService.checkFriendRequestSent(userId, foundUser.getId())){
            relationship.setLength(0);
            relationship.append("friend-sent");
        }
        else if(friendRequestService.checkFriendRequestSent(foundUser.getId(),userId)){
            relationship.setLength(0);
            relationship.append("pending-friend-request");
        }


        return GetUserInfoResponse.builder().
                userId(foundUser.getId()).
                name(foundUser.getName()).
                relationship(relationship.toString()).
                build();
    }

    public List<GetFriendRequestListResponse> getFriendRequestList(String userId) {
        List<GetFriendRequestListResponse> response = new ArrayList<>();
        try {
            log.info("UserID: {}", userId);
            response = userRepository.getFriendRequestList(userId);
//            log.info("FriendRequest List: {}", response);
            return response;
        } catch (NestedRuntimeException | HibernateException e ) {
            // Handle JPA data access exception
            System.err.println("Error: " + e.getMessage());
            // You can also log the exception or rethrow it as a custom exception
            throw e;
        }

    }

    public List<GetFriendListResponse> getFriendList(String userId) {
        List<GetFriendListResponse> response = new ArrayList<>();
        try {
            log.info("UserID: {}", userId);
            response = userRepository.getFriendList(userId);
//            log.info("FriendRequest List: {}", response);
            return response;
        } catch (NestedRuntimeException | HibernateException e ) {
            // Handle JPA data access exception
            System.err.println("Error: " + e.getMessage());
            // You can also log the exception or rethrow it as a custom exception
            throw e;
        }

    }

    public List<GetBlockListResponse> getBlockList(String userId) {
        List<GetBlockListResponse> response = new ArrayList<>();
        try {
            log.info("UserID: {}", userId);
            response = userRepository.getBlockList(userId);
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
