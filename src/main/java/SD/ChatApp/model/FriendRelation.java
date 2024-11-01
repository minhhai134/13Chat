package SD.ChatApp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "friend_relation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userId;

    private String friendId;
}
