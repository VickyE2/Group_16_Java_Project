package group.sixteen.bowen.two.screens;

import group.sixteen.bowen.two.ScreenManager;
import javafx.scene.Parent;

public interface Screen {
    Parent getView(ScreenManager manager); // returns UI root
}