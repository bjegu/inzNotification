package NotificationSystem.notification;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface NotificationRepository extends CrudRepository<Notification, String> {

    @Override
    List<Notification> findAll();
}
