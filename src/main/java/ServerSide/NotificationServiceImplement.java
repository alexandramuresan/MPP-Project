package ServerSide;

import Domain.Excursie;
import Domain.Notification;
import Domain.NotificationType;
import Services.AppService.IAgentieNotificationService;
import org.springframework.jms.core.JmsOperations;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public class NotificationServiceImplement implements IAgentieNotificationService {
    private JmsOperations jmsOperations;
    public NotificationServiceImplement(JmsOperations jmsOperations){
        this.jmsOperations = jmsOperations;
    }
    @Override
    public void rezervareEfectuata(Excursie e) {
        Notification notif = new Notification(NotificationType.SHOW_UPDATED,e);
        jmsOperations.convertAndSend(notif);
    }
}
