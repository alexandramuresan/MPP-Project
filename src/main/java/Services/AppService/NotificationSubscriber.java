package Services.AppService;

import Domain.Notification;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public interface NotificationSubscriber {
    void notificationReceived(Notification notif);
}
