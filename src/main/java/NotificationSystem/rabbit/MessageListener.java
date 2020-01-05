package NotificationSystem.rabbit;

import NotificationSystem.notification.PersistenceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static NotificationSystem.rabbit.NotificationQueues.*;

@Service
public class MessageListener {

    private Map<String, NotificationMessageDTO> msgs = new HashMap<>();
    @Autowired
    private PersistenceService persistenceService;

    @RabbitListener(queues = EVENT_REMINDER)
    public void receiveEventReminder(final NotificationMessageDTO message) {
        System.out.println("Received message at: " + EVENT_REMINDER + " :" + message);
        msgs.put(message.getId(), message);
//        System.out.println("Read all messages");
        System.out.println("....");
        msgs.values().forEach(System.out::println);
        System.out.println("....");
        persistenceService.persistDTO(message, EVENT_REMINDER);
    }

    @RabbitListener(queues = CLIENT_AGREEMENT)
    public void receiveAgreementFinish(final NotificationMessageDTO message) {
        System.out.println("Received message at: " + CLIENT_AGREEMENT + " :" + message);
        msgs.put(message.getId(), message);
//        System.out.println("Read all messages");
        System.out.println("....");
        msgs.values().forEach(System.out::println);
        System.out.println("....");
        persistenceService.persistDTO(message, CLIENT_AGREEMENT);
    }


    @RabbitListener(queues = DEACTIVATION)
    public void receiveMessage(final NotificationMessageDTO message) {
        System.out.println("Received message at: " + DEACTIVATION + " remove:" + message.getId());
        msgs.put(message.getId(), message);
//        System.out.println("Read all messages");
        System.out.println("....");
        msgs.values().forEach(System.out::println);
        System.out.println("....");
        persistenceService.remove(message.getId());
    }

}
