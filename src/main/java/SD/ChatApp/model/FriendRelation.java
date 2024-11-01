package SD.ChatApp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "friendrelation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String user1Id;

    private String user2Id;
}
