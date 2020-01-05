package NotificationSystem.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Notification implements Serializable {

    @Id
    private String id;

    @Column
    private Timestamp date;

    @Column
    private String sendTo;

    @Column
    private String addressee;

    @Column
    private String type;

    @Column
    private boolean active;

}
