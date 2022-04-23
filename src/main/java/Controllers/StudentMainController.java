package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentMainController implements Initializable {
    @FXML
    private VBox content;
    public void scheduleHanler(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void attendanceHanler(ActionEvent event) {
    }

    public void courseHandler(ActionEvent event) {
    }

    public void logOutHandler(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
        Stage newWindow = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/login-view.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        newWindow.setTitle("Đăng nhập");
        newWindow.setScene(scene);
        newWindow.show();
    }
}
