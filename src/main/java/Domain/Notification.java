package Domain;

/**
 * Created by Alexandra Muresan on 5/14/2017.
 */
public class Notification {

    private NotificationType type;
    private String user, sender, messageText;
    private Excursie excursie;

    public Notification() {
    }

    public Notification(NotificationType type,Excursie excursie) {

        this.type = type;
        this.excursie = excursie;
    }

    public Notification(NotificationType type, String user) {
        this.type = type;
        this.user = user;
    }

    public Notification(NotificationType type, String sender, String messageText) {
        this.type = type;
        this.sender=sender;
        this.messageText=messageText;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTextMessage() {
        return messageText;
    }

    public void setTextMessage(String message) {
        this.messageText = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Notification{ " +
                "type=" + type +
                ", user=" + user +
                ", sender=" + sender +
                ", message=" + messageText +
                '}';
    }
}
