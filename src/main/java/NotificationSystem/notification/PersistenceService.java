package NotificationSystem.notification;

import NotificationSystem.rabbit.NotificationMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Stream;

@Service
public class PersistenceService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void persist(Notification notification){
        notificationRepository.save(notification);
    }

    void setAsCompleted(Notification notification){
        notification.setActive(false);
        notificationRepository.save(notification);
    }

    public void persistDTO(NotificationMessageDTO dto, String type){
        notificationRepository.save(convert(dto, type));
    }

    Stream<Notification> getActiveNotificationStream(){
        return notificationRepository.findAll().stream().filter(Notification::isActive);
    }

    public void remove(String id){
        notificationRepository.deleteById(id);
    }

    private Notification convert(NotificationMessageDTO dto, String type){
        return Notification.builder()
                .id(dto.getId())
                .date(dto.getDateTime())
                .addressee(dto.getAddressee())
                .sendTo(dto.getSendTo())
                .type(type)
                .active(true)
                .build();
    }
}
