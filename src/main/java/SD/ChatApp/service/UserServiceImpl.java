package SD.ChatApp.service;

import SD.ChatApp.dto.user.GetUserInfoRequest;
import SD.ChatApp.dto.user.LoginRequest;
import SD.ChatApp.dto.user.RegisterRequest;
import SD.ChatApp.exception.user.UserNameExistedException;
import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.model.User;
import SD.ChatApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlockService blockService;

    @Autowired
    private FriendService friendService;

    public User createUser(RegisterRequest request) {

        if(userRepository.findByUsername(request.getUsername()).isPresent())
            throw new UserNameExistedException();

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

    public User getUserInfo(GetUserInfoRequest request){
        User foundUser = userRepository.findByUsername(request.getSearchName())
                .orElseThrow(() -> new UserNotFoundException());

        if(blockService.checkBlockstatus(request.getUserId(), foundUser.getId()) || blockService.checkBlockstatus(foundUser.getId(), request.getUserId()))
            throw new UserNotFoundException();





        return null;
    }


}
