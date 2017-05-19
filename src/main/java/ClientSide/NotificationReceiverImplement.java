package ClientSide;

import Domain.Notification;
import Services.AppService.NotificationReceiver;
import Services.AppService.NotificationSubscriber;
import org.springframework.jms.core.JmsOperations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public class NotificationReceiverImplement implements NotificationReceiver {

    private JmsOperations jmsOperations;
    private boolean running;
    ExecutorService service;
    NotificationSubscriber subscriber;

    public NotificationReceiverImplement(JmsOperations operations){ this.jmsOperations = operations;}
    @Override
    public void start(NotificationSubscriber subscriber) {
        System.out.println("Starting notification receiver ...");
        running=true;
        this.subscriber=subscriber;
        service = Executors.newSingleThreadExecutor();
        service.submit(()->{run();});
    }

    private void run(){
        while(running){
            Notification notif=(Notification)jmsOperations.receiveAndConvert();
            System.out.println("Received Notification... "+notif);
            subscriber.notificationReceived(notif);

        }
    }
    @Override
    public void stop() {
        running=false;
        try {
            service.awaitTermination(100, TimeUnit.MILLISECONDS);
            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopped notification receiver");
    }
}
