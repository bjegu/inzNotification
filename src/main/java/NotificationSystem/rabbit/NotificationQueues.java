package NotificationSystem.rabbit;

import java.util.Arrays;
import java.util.List;

public class NotificationQueues {

    public static final String CLIENT_AGREEMENT = "CLIENT_AGREEMENT";
    public static final String EVENT_REMINDER = "EVENT_REMINDER";
    static final String DEACTIVATION = "DEACTIVATION";

    static List<String> getStringList() {
        return Arrays.asList(CLIENT_AGREEMENT, EVENT_REMINDER, DEACTIVATION);
    }

}
