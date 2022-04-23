import DAO.ScheduleDAO;
import Entities.Schedule;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class Main extends Application {
    Stage window;
    Scene scene, scene2;
    @Override
    public void start(Stage stage) throws IOException {
        window =stage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/layouts/login-view.fxml"));
        scene = new Scene(loader.load());
//        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

//        FXMLLoader loader2 = new FXMLLoader(Main.class.getResource("/teacher-main-view.fxml"));
//        scene2 = new Scene(loader2.load(), 600, 400);
//        stage.setScene(scene2);

    }
    public static void main(String[] args) {
        launch();
    }
}
