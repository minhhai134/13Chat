package SD.ChatApp.repository.conversation;

import SD.ChatApp.model.conversation.GroupMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMetaDataRepository extends JpaRepository<GroupMetaData, String> {
}
