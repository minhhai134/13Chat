package SD.ChatApp.model.conversation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_metadata")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMetaData {

    @Id
//    @ManyToOne
//    @JoinColumn(name = "group_id", nullable = false)
    private String groupId;

    private String groupName;

    private String adminId;

}
