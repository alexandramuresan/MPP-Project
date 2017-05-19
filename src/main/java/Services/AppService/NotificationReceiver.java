package Services.AppService;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public interface NotificationReceiver {
    void start(NotificationSubscriber subscriber);
    void stop();
}
