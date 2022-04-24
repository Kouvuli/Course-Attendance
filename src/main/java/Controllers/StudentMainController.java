package Controllers;

import DAO.StudentDAO;
import Entities.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentMainController implements Initializable {
    @FXML
    private VBox content;
    @FXML
    private Label userIdLabel;

    @FXML
    private Label usernameLabel;
    private int studentId;
    public void scheduleHanler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/student-schedule-view.fxml"));
        StudentScheduleController controller=new StudentScheduleController();
        controller.setValue(studentId);
        loader.setController(controller);

        Parent root = null;
        root = (Parent) loader.load();

        content.getChildren().setAll(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StudentDAO dao=new StudentDAO();
        Student student=dao.getStudentById(studentId);
        usernameLabel.setText(student.getName());
        userIdLabel.setText(String.valueOf(studentId));
    }

    public void attendanceHanler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/student-attendance-result-view.fxml"));
        StudentAttendanceResultController controller=new StudentAttendanceResultController();
        controller.setValue(studentId);
        loader.setController(controller);

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
    public void setValue(int studentId){
        this.studentId=studentId;
    }
}
