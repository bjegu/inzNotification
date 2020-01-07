package NotificationSystem.notification;

import NotificationSystem.rabbit.NotificationQueues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class EmailService {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Scheduled(cron = "${cron.expression}")
    public void sendNotifications() {
        System.out.println("Scheduler run");
        persistenceService.getActiveNotificationStream()
                .filter(this::shouldBeSent)
                .forEach(this::sendEmail);
    }

    private void sendEmail(Notification notification) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(notification.getSendTo());

            msg.setSubject(prepareTitle(notification));
            msg.setText(prepareMessage(notification));

            javaMailSender.send(msg);
            persistenceService.setAsCompleted(notification);
        } catch (MailException e) {
            System.out.println(String.format("Failed Sending Email Notification To  %s with error: %s", notification.getSendTo(), e.getMessage()));
        } catch (NoSupportedNotification e) {
            System.out.println(String.format("%s is unsupported notification for id: %s", notification.getType(), notification.getId()));
        }
    }

    private String prepareTitle(Notification notification) throws NoSupportedNotification {
        if (NotificationQueues.CLIENT_AGREEMENT.equals(notification.getType())) {
            return "Agreement end soon";
        }
        if (NotificationQueues.EVENT_REMINDER.equals(notification.getType())) {
            return "Your event is coming.";
        }
        throw new NoSupportedNotification(notification.getType());
    }

    private String prepareMessage(Notification notification) throws NoSupportedNotification {
        if (NotificationQueues.CLIENT_AGREEMENT.equals(notification.getType())) {
            return String.format("Dear %s, Yours agreement finishing at the date of %s. Your Insurance Agent", notification.getAddressee(), dateFormat.format(notification.getDate()));
        }
        if (NotificationQueues.EVENT_REMINDER.equals(notification.getType())) {
            return String.format("Dear %s, Your have a appointment tomorrow (%s) at %s. Your Insurance Agent", notification.getAddressee(), dateFormat.format(notification.getDate()), timeFormat.format(notification.getDate()));
        }
        throw new NoSupportedNotification(notification.getType());
    }

    private boolean shouldBeSent(Notification notification){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        return notification.isActive() && tomorrow.compareTo(notification.getDate()) > -1;
    }
}