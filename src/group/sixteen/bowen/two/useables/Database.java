package group.sixteen.bowen.two.useables;

import group.sixteen.bowen.two.entities.OutboxMail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Database {
    private static final String FILE_PATH = "outbox_database.txt";
    private static final String SEPARATOR = " <|> "; // Keeps the text file readable

    public static void saveMail(OutboxMail mail) {
        try {
            // Encrypt the sensitive fields
            String encTitle = CryptoUtils.encrypt(mail.title);
            String encContent = CryptoUtils.encrypt(mail.content);

            // Format: Sender | Recipient | Timestamp | EncTitle | EncContent
            String line = String.join(SEPARATOR,
                    mail.sender,
                    mail.recipient,
                    mail.timestamp.toString(),
                    encTitle,
                    encContent
            ) + System.lineSeparator();

            // Append to the file (creates the file if it doesn't exist)
            Files.write(Paths.get(FILE_PATH), line.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            System.out.println("Mail saved to database.");

        } catch (Exception e) {
            System.err.println("Failed to save mail: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static ObservableList<OutboxMail> getMailsForSender(String targetSenderEmail) {
        ObservableList<OutboxMail> data = FXCollections.observableArrayList();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return data; // Return empty list if no database exists yet
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" <\\|> ");
                if (parts.length < 5) continue; // Skip corrupted lines

                String sender = parts[0];

                // Filter: Only process this line if it belongs to the active user
                if (sender.equalsIgnoreCase(targetSenderEmail)) {
                    String recipient = parts[1];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[2]);

                    // Decrypt the sensitive fields
                    String title = CryptoUtils.decrypt(parts[3]);
                    String content = CryptoUtils.decrypt(parts[4]);

                    // Add to our observable list
                    data.add(new OutboxMail(recipient, content, title, sender, timestamp));
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to read database: " + e.getMessage());
            e.printStackTrace();
        }

        return data;
    }

    public static ObservableList<OutboxMail> createDummyData() {
        // 1. Initialize the list
        ObservableList<OutboxMail> data = FXCollections.observableArrayList();

        // 2. Add fabricated OutboxMail objects
        data.add(new OutboxMail(
                "alice@example.com",
                "Hey Alice, just checking in on the meeting minutes.",
                "Meeting Update",
                "me@example.com",
                LocalDateTime.now().minusHours(2)
        ));

        data.add(new OutboxMail(
                "bob@tech.com",
                "The project files are attached to this email. Please review.",
                "Project Files",
                "me@example.com",
                LocalDateTime.now().minusDays(1)
        ));

        data.add(new OutboxMail(
                "jane@design.io",
                "Hello! Long time no see. Let's grab coffee soon.",
                "Hello!",
                "me@example.com",
                LocalDateTime.now().minusMinutes(45)
        ));

        return data;
    }
}
