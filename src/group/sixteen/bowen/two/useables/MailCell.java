package group.sixteen.bowen.two.useables;

import group.sixteen.bowen.two.entities.OutboxMail;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

public class MailCell extends ListCell<OutboxMail> {
    private final VBox card = new VBox(5);
    private final Label senderLabel = new Label();
    private final Label titleLabel = new Label();

    public MailCell() {
        // Style the "Rounded Rectangle" Card
        card.setPadding(new Insets(10, 15, 10, 15));
        card.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 5;
            -fx-border-color: #e0e0e0;
            -fx-border-radius: 5;
            -fx-border-width: 1;
        """);
        
        senderLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #202124;");
        titleLabel.setStyle("-fx-text-fill: #5f6368;");
        
        card.getChildren().addAll(senderLabel, titleLabel);
    }

    @Override
    protected void updateItem(OutboxMail mail, boolean empty) {
        super.updateItem(mail, empty);
        if (empty || mail == null) {
            setGraphic(null);
            setStyle("-fx-background-color: transparent;"); // Keep the list background clean
        } else {
            senderLabel.setText("To: " + mail.recipient);
            titleLabel.setText(mail.title);
            setGraphic(card);
            
            // Add a little margin between items
            setPadding(new Insets(5, 10, 5, 10)); 
            setStyle("-fx-background-color: transparent;");
        }
    }
}