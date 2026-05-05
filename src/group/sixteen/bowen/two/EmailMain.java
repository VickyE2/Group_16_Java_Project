package group.sixteen.bowen.two;

import group.sixteen.bowen.two.screens.HomeScreen;
import group.sixteen.bowen.two.screens.LoginScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class EmailMain extends Application {
    public static String email;
    public static String password;

    @Override
    public void start(Stage stage) {
        ScreenManager manager = new ScreenManager(stage);

        manager.setScreen(new LoginScreen());

        stage.setTitle("Email Application - Group 16");
        stage.show();
    }
}
