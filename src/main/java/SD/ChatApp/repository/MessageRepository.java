package SD.ChatApp.repository;

import SD.ChatApp.model.conversation.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {


}
