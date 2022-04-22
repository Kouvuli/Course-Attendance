package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherAttendanceController implements Initializable {
    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> week10Col;

    @FXML
    private TableColumn<?, ?> week11Col;

    @FXML
    private TableColumn<?, ?> week12Col;

    @FXML
    private TableColumn<?, ?> week13Col;

    @FXML
    private TableColumn<?, ?> week14Col;

    @FXML
    private TableColumn<?, ?> week15Col;

    @FXML
    private TableColumn<?, ?> week1Col;

    @FXML
    private TableColumn<?, ?> week2Col;

    @FXML
    private TableColumn<?, ?> week3Col;

    @FXML
    private TableColumn<?, ?> week4Col;

    @FXML
    private TableColumn<?, ?> week5Col;

    @FXML
    private TableColumn<?, ?> week6Col;

    @FXML
    private TableColumn<?, ?> week7Col;

    @FXML
    private TableColumn<?, ?> week8Col;

    @FXML
    private TableColumn<?, ?> week9Col;

    @FXML
    void applyHandler(ActionEvent event) {


    }

    @FXML
    void cancelHandler(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
