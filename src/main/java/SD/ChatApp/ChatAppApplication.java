package SD.ChatApp;

import SD.ChatApp.model.User;
import SD.ChatApp.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ChatAppApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(ChatAppApplication.class, args);

		UserRepository userRepository = context.getBean(UserRepository.class);


		for (int i=1;i<=4;i++) {
			userRepository.save(User.builder().
					username("username"+Integer.toString(i)).
					password("1").
					name("user"+Integer.toString(i)).
					onlineStatus("on").
					build());
		}
	}

}
