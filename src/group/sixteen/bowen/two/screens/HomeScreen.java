package group.sixteen.bowen.two.screens;

import group.sixteen.bowen.two.EmailMain;
import group.sixteen.bowen.two.ScreenManager;
import group.sixteen.bowen.two.entities.OutboxMail;
import group.sixteen.bowen.two.useables.MailCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import static group.sixteen.bowen.two.useables.Database.createDummyData;
import static group.sixteen.bowen.two.useables.Database.getMailsForSender;

public class HomeScreen implements Screen {

    @Override
    public Parent getView(ScreenManager manager) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f6f8;");

        // Top bar
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 0, 15, 0));

        Label title = new Label("Home");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        HBox spacer = new HBox();
        spacer.setMinWidth(10);
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        SVGPath exitIcon = new SVGPath();
        exitIcon.setContent("M10 22h-6c-1.1 0-2-.9-2-2v-16c0-1.1.9-2 2-2h6v2h-6v16h6v2zm10.7-10.1l-4.5-4.5-1.4 1.4 2.1 2.1h-7.8v2h7.8l-2.1 2.1 1.4 1.4 4.5-4.5z");
        exitIcon.setFill(Color.web("#8d191f")); // Start with a neutral gray

        Button logoutButton = new Button();
        logoutButton.setGraphic(exitIcon);
        logoutButton.setTooltip(new Tooltip("Log out of this account"));

        logoutButton.setStyle("""
            -fx-background-color: transparent;
            -fx-cursor: hand;
            -fx-padding: 10;
            -fx-background-radius: 50;
        """);

        logoutButton.setOnMouseEntered(e -> {
            logoutButton.setStyle(logoutButton.getStyle() + "-fx-background-color: #fceae9;"); // Light red tint
            exitIcon.setFill(Color.web("#e74c3c")); // Icon turns vibrant red
        });

        logoutButton.setOnMouseExited(e -> {
            logoutButton.setStyle(logoutButton.getStyle().replace("-fx-background-color: #fceae9;", "-fx-background-color: transparent;"));
            exitIcon.setFill(Color.web("#8d191f")); // Back to gray
        });

        logoutButton.setOnAction(e -> {
            EmailMain.email = null;
            EmailMain.password = null;
            manager.setScreen(new LoginScreen());
        });

        topBar.getChildren().addAll(title, spacer, logoutButton);
        root.setTop(topBar);

        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(15));
        centerBox.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 12;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 2);
        """); // Added a soft shadow instead of a hard border

        Label listTitle = new Label("Sent Messages");
        listTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ListView<OutboxMail> messageList = new ListView<>();
        messageList.setItems(getMailsForSender(EmailMain.email));
        messageList.setCellFactory(_ -> new MailCell());
        messageList.setOnMouseClicked(_ -> {
            OutboxMail selected = messageList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showMailPopup(selected);
            }
        });

        messageList.setStyle("""
            -fx-background-color: transparent;
            -fx-control-inner-background: transparent;
            -fx-background-insets: 0;
            -fx-padding: 0;
        """);
        VBox.setVgrow(messageList, Priority.ALWAYS); // Make sure the list fills the card
        centerBox.getChildren().addAll(listTitle, messageList);
        root.setCenter(centerBox);

        // Bottom-right compose button
        StackPane bottomPane = new StackPane();
        bottomPane.setPadding(new Insets(20, 0, 0, 0));

        SVGPath plusIcon = new SVGPath();
        plusIcon.setContent("M12 5c.6 0 1 .4 1 1v5h5c.6 0 1 .4 1 1s-.4 1-1 1h-5v5c0 .6-.4 1-1 1s-1-.4-1-1v-5h-5c-.6 0-1-.4-1-1s.4-1 1-1h5v-5c0-.6.4-1 1-1z");
        plusIcon.setFill(Color.WHITE);

        Button addButton = new Button();
        addButton.setGraphic(plusIcon);

        double size = 40;
        addButton.setMinSize(size, size);
        addButton.setPrefSize(size, size);
        addButton.setMaxSize(size, size);

        addButton.setStyle("""
            -fx-background-color: #2ecc71; /* Modern Emerald Green */
            -fx-background-radius: 50;
            -fx-cursor: hand;
        """);

        DropShadow fabShadow = new DropShadow(8, Color.rgb(0, 0, 0, 0.2));
        addButton.setEffect(fabShadow);

        addButton.setOnMouseEntered(_ -> {
            addButton.setStyle(addButton.getStyle() + "-fx-background-color: #27ae60;");
            fabShadow.setRadius(12); // "Lift" effect on hover
        });
        addButton.setOnMouseExited(_ -> addButton.setStyle(addButton.getStyle().replace("-fx-background-color: #27ae60;", "-fx-background-color: #2ecc71;")));
        addButton.setOnAction(_ -> manager.setScreen(new ComposeScreen()));

        StackPane.setAlignment(addButton, Pos.BOTTOM_RIGHT);
        bottomPane.getChildren().add(addButton);
        root.setBottom(bottomPane);

        return root;
    }

    private void showMailPopup(OutboxMail mail) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message Details");
        alert.setHeaderText("From: " + mail.sender);

        // Create the content area
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));

        Label title = new Label(mail.title);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        title.setWrapText(true);

        Label body = new Label(mail.content);
        body.setWrapText(true);
        body.setPrefWidth(400);

        content.getChildren().addAll(title, new Separator(), body);

        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }
}