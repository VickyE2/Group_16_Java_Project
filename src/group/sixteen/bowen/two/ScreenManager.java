package group.sixteen.bowen.two;

import group.sixteen.bowen.two.screens.Screen;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenManager {
    private final Stage stage;

    public ScreenManager(Stage stage) {
        this.stage = stage;
    }

    public void setScreen(Screen screen) {
        Scene scene = new Scene(screen.getView(this), 500, 400);
        stage.setScene(scene);
    }
}
