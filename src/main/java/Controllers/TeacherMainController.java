package Controllers;

import DAO.TeacherDAO;
import Entities.Teacher;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeacherMainController implements Initializable {


    @FXML
    private VBox content;

    @FXML
    private Label userIdLabel;

    @FXML
    private Label usernameLabel;

    private int userId;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userIdLabel.setText(String.valueOf(userId));
        TeacherDAO dao = new TeacherDAO();
        Teacher teacher = dao.getTeacherById(userId);
        usernameLabel.setText(teacher.getName());
    }

    public void courseHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/teacher-course-view.fxml"));
        Parent root = null;
        root = (Parent) loader.load();


        content.getChildren().setAll(root);
    }

    public void scheduleHanler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/teacher-schedule-view.fxml"));
        Parent root = null;
        root = (Parent) loader.load();


        content.getChildren().setAll(root);

    }


    public void studentHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/teacher-student-view.fxml"));
        Parent root = null;
        root = (Parent) loader.load();

        content.getChildren().setAll(root);
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

    public void teacherHandler(ActionEvent event) {
    }
    public void setValue(int id){
        this.userId=id;
    }
}
