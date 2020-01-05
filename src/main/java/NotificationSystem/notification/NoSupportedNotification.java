package NotificationSystem.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoSupportedNotification extends Exception {

    private String typeValue;
}
