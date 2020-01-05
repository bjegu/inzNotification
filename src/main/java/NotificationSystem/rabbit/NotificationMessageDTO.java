package NotificationSystem.rabbit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessageDTO implements Serializable {

    private String id;
    private String addressee;
    private String sendTo;
    private Timestamp dateTime;

}
