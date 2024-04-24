package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/mainView.fxml")));
        Scene scene = new Scene(root);

        Image image = new Image("icons/icon.png");
        primaryStage.getIcons().add(image);


        primaryStage.setTitle("Schneider Electric");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(1100);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
