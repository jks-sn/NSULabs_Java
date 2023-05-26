package connection;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Message implements Serializable {
    private final String message;
    private final MessageType type;
    private final Calendar date;
    private User user;

    public Message(String message, MessageType type) {
        this.message = message;
        this.type = type;
        this.date = new GregorianCalendar();
    }

    public Message(String message, MessageType type, User user) {
        this(message, type);
        this.user = user;
    }

    public Message(@NotNull Message message) {
        this(message.message, message.type, message.user);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getType() {
        return type;
    }

    public int getSenderID() {
        return user.getId();
    }

    public void setSenderID(int senderID) {user.setId(senderID);}
    public String getSenderName() {
        return user.getName();
    }

    public void setSenderName(String senderName) {user.setName(senderName);}

    public Calendar getDate() {
        return date;
    }
}

