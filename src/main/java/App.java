import controller.DatabaseConnection;
import controller.InputController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane root;


        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/view/Input.fxml")));
        fxmlLoader.setController(InputController.getInstance());
        root = fxmlLoader.load();
        InputController.getInstance().initialize();

        Scene scene = new Scene(root);
        stage.setMinHeight(442);
        stage.setMinWidth(826);

        stage.setTitle("Autorückgabe");
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            DatabaseConnection.getInstance().closeConnection();
            Platform.exit(); //Close all Open Windows
            System.exit(0);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
