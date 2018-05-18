import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.MainController;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] argv) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/view/MainWindow.fxml"));

        AnchorPane rootLayout;
        try {
            rootLayout = loader.load();
            MainController rootController = loader.getController();
            primaryStage.setScene(new Scene(rootLayout, 525, 250));
            primaryStage.setTitle("Main");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
