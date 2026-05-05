package group.sixteen.bowen.two.screens;

import group.sixteen.bowen.two.EmailMain;
import group.sixteen.bowen.two.ScreenManager;
import group.sixteen.bowen.two.entities.OutboxMail;
import group.sixteen.bowen.two.useables.Database;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class ComposeScreen implements Screen {

    @Override
    public Parent getView(ScreenManager manager) {

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f3f4f6;"); // Modern soft gray background

        // ================= TOP BAR =================
        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 0, 20, 0));

        // Modern Back Button with SVG Arrow
        SVGPath backIcon = new SVGPath();
        backIcon.setContent("M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z");
        backIcon.setFill(Color.web("#4b5563"));

        Button backButton = new Button();
        backButton.setGraphic(backIcon);
        backButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-background-radius: 50;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #e5e7eb; -fx-cursor: hand; -fx-background-radius: 50;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-background-radius: 50;"));
        backButton.setOnAction(e -> manager.setScreen(new HomeScreen()));

        Label title = new Label("New Message");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        topBar.getChildren().addAll(backButton, title);
        root.setTop(topBar);

        // ================= FORM CARD =================
        VBox form = new VBox(15);
        form.setPadding(new Insets(25));
        form.setMaxWidth(600); // Prevents the form from stretching too wide on large screens
        form.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 12;
        """);
        
        // Add a soft, modern drop shadow to the card
        form.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.05)));

        // Shared input style
        String inputStyle = """
            -fx-background-color: #f9fafb;
            -fx-border-color: #e5e7eb;
            -fx-border-radius: 6;
            -fx-background-radius: 6;
            -fx-padding: 8 12;
            -fx-font-size: 14px;
        """;
        String labelStyle = "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #6b7280;";

        // TO FIELD with Real-Time Validation
        Label toLabel = new Label("To");
        toLabel.setStyle(labelStyle);
        TextField toField = new TextField();
        toField.setPromptText("recipient@example.com");
        toField.setStyle(inputStyle);
        
        toField.textProperty().addListener((obs, oldV, newV) -> {
            if (newV.isEmpty()) {
                toField.setStyle(inputStyle);
            } else if (isEmailValid(newV)) {
                toField.setStyle(inputStyle + "-fx-border-color: #10b981; -fx-background-color: #ecfdf5;"); // Green
            } else {
                toField.setStyle(inputStyle + "-fx-border-color: #ef4444; -fx-background-color: #fef2f2;"); // Red
            }
        });

        // SUBJECT
        Label subjectLabel = new Label("Subject");
        subjectLabel.setStyle(labelStyle);
        TextField subjectField = new TextField();
        subjectField.setPromptText("What is this regarding?");
        subjectField.setStyle(inputStyle);

        // MESSAGE
        Label messageLabel = new Label("Message");
        messageLabel.setStyle(labelStyle);
        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Write your message here...");
        messageArea.setStyle(inputStyle);
        messageArea.setPrefHeight(250);
        messageArea.setWrapText(true); // Ensures text doesn't scroll infinitely to the right

        form.getChildren().addAll(
                toLabel, toField,
                subjectLabel, subjectField,
                messageLabel, messageArea
        );

        // Center the form in the BorderPane
        VBox centerWrapper = new VBox(form);
        centerWrapper.setAlignment(Pos.TOP_CENTER);
        root.setCenter(centerWrapper);

        // ================= SEND BUTTON =================
        StackPane bottom = new StackPane();
        bottom.setPadding(new Insets(20, 0, 0, 0));

        // Paper Plane Icon
        SVGPath sendIcon = new SVGPath();
        sendIcon.setContent("M2.01 21L23 12 2.01 3 2 10l15 2-15 2z");
        sendIcon.setFill(Color.WHITE);
        sendIcon.setScaleX(0.7);
        sendIcon.setScaleY(0.7);

        Button sendButton = new Button("Send Message");
        sendButton.setGraphic(sendIcon);
        sendButton.setStyle("""
            -fx-background-color: #3b82f6; /* Modern Blue */
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-font-size: 14px;
            -fx-background-radius: 8;
            -fx-padding: 10 24;
            -fx-cursor: hand;
        """);
        
        sendButton.setOnMouseEntered(e -> sendButton.setStyle(sendButton.getStyle() + "-fx-background-color: #2563eb;"));
        sendButton.setOnMouseExited(e -> sendButton.setStyle(sendButton.getStyle().replace("-fx-background-color: #2563eb;", "-fx-background-color: #3b82f6;")));

        StackPane.setAlignment(sendButton, Pos.BOTTOM_RIGHT);
        bottom.getChildren().add(sendButton);
        form.getChildren().add(bottom); // Moved inside the form card for a cleaner look

        // ================= ASYNC LOGIC =================
        sendButton.setOnAction(e -> {
            String to = toField.getText();
            String subject = subjectField.getText();
            String message = messageArea.getText();

            if (!isEmailValid(to)) {
                showError("Please enter a valid email address.");
                toField.requestFocus();
                return;
            }

            if (message.isEmpty()) {
                showError("Message body cannot be empty.");
                messageArea.requestFocus();
                return;
            }

            // UI Feedback state
            sendButton.setDisable(true);
            sendButton.setText("Sending...");
            sendButton.setGraphic(null); // Hide icon temporarily

            // Send in background so the UI doesn't freeze
            CompletableFuture.runAsync(() -> {
                try {
                    sendEmail(to, subject, message);

                    // Update UI on the main JavaFX thread
                    Platform.runLater(() -> {
                        Database.saveMail(new OutboxMail(to, message, subject));
                        showSuccess("Email sent successfully!");
                        manager.setScreen(new HomeScreen()); // Go back home on success
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Platform.runLater(() -> {
                        showError("Failed to send email. Check your connection or credentials.");
                        // Reset button
                        sendButton.setDisable(false);
                        sendButton.setText("Send Message");
                        sendButton.setGraphic(sendIcon);
                    });
                }
            });
        });

        return root;
    }

    // ================= EMAIL LOGIC =================
    private void sendEmail(String to, String subject, String body) throws Exception {
        String fromEmail = EmailMain.email;
        String password = EmailMain.password;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    // ================= HELPERS =================
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showSuccess(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private boolean isEmailValid(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}