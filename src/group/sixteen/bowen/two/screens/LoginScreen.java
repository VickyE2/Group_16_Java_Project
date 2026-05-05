package group.sixteen.bowen.two.screens;

import group.sixteen.bowen.two.EmailMain;
import group.sixteen.bowen.two.ScreenManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LoginScreen implements Screen {
    @Override
    public Parent getView(ScreenManager manager) {
        // Root container with a soft professional background
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #f3f4f6;");

        // The "Login Card"
        VBox card = new VBox(20);
        card.setPadding(new Insets(40, 30, 40, 30));
        card.setAlignment(Pos.CENTER);
        card.setMaxSize(350, 450); // Increased height for better breathing room

        // Modern Style for the card
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        card.setEffect(new DropShadow(15, Color.rgb(0, 0, 0, 0.1)));

        // Title
        Label title = new Label("Welcome Back");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        // Common styles for input fields
        String fieldStyle = """
            -fx-background-color: #f9fafb;
            -fx-border-color: #e5e7eb;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
            -fx-padding: 10;
        """;

        String labelStyle = "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #6b7280;";

        // EMAIL SECTION
        VBox emailBox = new VBox(8);
        Label emailLabel = new Label("Email Address");
        emailLabel.setStyle(labelStyle);

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setStyle(fieldStyle);
        emailField.setPrefHeight(45);

        // Dynamic Validation Visuals
        emailField.textProperty().addListener((_, _, newValue) -> {
            if (newValue.isEmpty()) {
                emailField.setStyle(fieldStyle + "-fx-border-color: #ef4444;"); // Red
            } else if (!isEmailValid(newValue)) {
                emailField.setStyle(fieldStyle + "-fx-border-color: #f59e0b;"); // Orange/Yellow
            } else {
                emailField.setStyle(fieldStyle + "-fx-border-color: #10b981;"); // Green
            }
        });
        emailBox.getChildren().addAll(emailLabel, emailField);

        // PASSWORD SECTION
        VBox passBox = new VBox(8);
        Label passwordLabel = new Label("App Password");
        passwordLabel.setStyle(labelStyle);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter app password");
        passwordField.setStyle(fieldStyle);
        passwordField.setPrefHeight(45);
        passBox.getChildren().addAll(passwordLabel, passwordField);

        // LOGIN BUTTON
        Button loginButton = new Button("Sign In");
        loginButton.setPrefHeight(45);
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setCursor(javafx.scene.Cursor.HAND);
        loginButton.setStyle("""
            -fx-background-color: #3b82f6; 
            -fx-text-fill: white; 
            -fx-font-size: 14px; 
            -fx-font-weight: bold;
            -fx-background-radius: 8;
        """);

        // Hover effect for button
        loginButton.setOnMouseEntered(_ -> loginButton.setStyle(loginButton.getStyle() + "-fx-background-color: #2563eb;"));
        loginButton.setOnMouseExited(_ -> loginButton.setStyle(loginButton.getStyle().replace("-fx-background-color: #2563eb;", "-fx-background-color: #3b82f6;")));

        loginButton.setOnAction(e -> {
            if (!isEmailValid(emailField.getText())) {
                showErrorPopup("Invalid Email", "Please enter a valid email address.");
                return;
            }
            if (passwordField.getText().isEmpty()) {
                showErrorPopup("Empty Password", "Please enter your app password.");
                return;
            }

            EmailMain.email = emailField.getText();
            EmailMain.password = passwordField.getText();

            manager.setScreen(new HomeScreen());
        });

        // Add everything to card
        card.getChildren().addAll(title, emailBox, passBox, new Region(), loginButton);

        root.getChildren().add(card);
        return root;
    }

    private void showErrorPopup(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isEmailValid(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
// hico nrwx wtxm pblv