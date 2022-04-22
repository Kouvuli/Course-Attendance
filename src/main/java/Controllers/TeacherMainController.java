package Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeacherMainController implements Initializable {
    @FXML
    private JFXButton attendanceBtn;

    @FXML
    private VBox content;

    @FXML
    private JFXButton courseBtn;

    @FXML
    private JFXButton scheduleBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void courseHandler(ActionEvent event) {

    }

    public void scheduleHanler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/teacher-schedule-view.fxml"));
        Parent root = null;
        root = (Parent) loader.load();
        TeacherScheduleController controller = loader.getController();

        content.getChildren().setAll(root);

    }

    public void attendanceHanler(ActionEvent event) {
    }
}
