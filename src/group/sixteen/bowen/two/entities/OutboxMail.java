package group.sixteen.bowen.two.entities;

import group.sixteen.bowen.two.EmailMain;

import java.time.LocalDateTime;

public class OutboxMail {
    public final String recipient;
    public final String title;
    public final String content;

    public final String sender;
    public final LocalDateTime timestamp;

    public OutboxMail(String recipient, String content, String title, String sender, LocalDateTime timestamp) {
        this.recipient = recipient;
        this.content = content;
        this.title = title;
        this.sender = sender;
        this.timestamp = timestamp;
    }
    public OutboxMail(String recipient, String content, String title, String sender) {
        this(recipient, content, title, sender, LocalDateTime.now());
    }
    public OutboxMail(String recipient, String content, String title) {
        this(recipient, content, title, EmailMain.email, LocalDateTime.now());
    }
}
