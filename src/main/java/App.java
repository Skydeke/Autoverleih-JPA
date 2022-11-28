import controller.DatabaseConnection;
import controller.InputController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();


        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/view/Input.fxml")));
        fxmlLoader.setController(InputController.getInstance());
        root = fxmlLoader.load();
        InputController.getInstance().initialize();

        Scene scene = new Scene(root);

        stage.setTitle("Autorückgabe");
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            DatabaseConnection.getInstance().closeConnection();
            Platform.exit(); //Close all Open Windows
            System.exit(0);
        });
        stage.show();

        InputController.getInstance().informationDialog("Datenbank-Verbindung herstellen",
                "Um die Verbindung zu unserem Datenbank-Server herzustellen muss dieser auf localhost:10110 laufen. \n" +
                        "Abänderung dieser Information ist möglich in /resources/META-INF/perrsistence.xml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
