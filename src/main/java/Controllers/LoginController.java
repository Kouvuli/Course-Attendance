package Controllers;

import DAO.StudentDAO;
import DAO.TeacherDAO;
import Entities.Student;
import Entities.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable  {
    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void handleCancleButton(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void handleLoginButton(ActionEvent event) {
        if (isInputValid()){
            StudentDAO studentDAO=new StudentDAO();
            TeacherDAO teacherDAO=new TeacherDAO();
            Student student=null;
            Teacher teacher=null;
            try {
                teacher=teacherDAO.getTeacherByUsername(username.getText());

            }catch (Exception e){
                try {

                    student=studentDAO.getStudentByUsername(username.getText());
                }catch (Exception exception){

                }
            }

            if(teacher==null&&student==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setContentText("Tên đăng nhập không tồn tại!");
                alert.showAndWait();
            }
            if(teacher!=null){
                boolean valuate = BCrypt.checkpw(password.getText(), teacher.getPassword());
                if(valuate){
                    Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
                    window.close();
                    Stage newWindow = new Stage();
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/layouts/teacher-main-view.fxml"));
                    TeacherMainController controller=new TeacherMainController();
                    controller.setValue(Integer.parseInt(username.getText()));
                    loader.setController(controller);
                    Parent root= null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(root);
                    newWindow.setTitle("Course Attendance");
                    newWindow.setScene(scene);
                    newWindow.show();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setContentText("Password không chinh xác!");
                    alert.showAndWait();
                }
            }
            if (student!=null){
                boolean valuate = BCrypt.checkpw(password.getText(), student.getPassword());
                if(valuate){
                    if(student.getIsFirst()){
                        Stage newWindow = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/change-password-dialog.fxml"));
                        ChangePasswordDialogController controller = new ChangePasswordDialogController();
                        controller.setValue(student);
                        loader.setController(controller);
                        Parent root= null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene scene = new Scene(root);
                        newWindow.setTitle("Đổi mật khẩu");
                        newWindow.setScene(scene);
                        newWindow.show();
                    }else{
                        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
                        window.close();
                        Stage newWindow = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/student-main-view.fxml"));
                        StudentMainController controller=new StudentMainController();
                        controller.setValue(Integer.parseInt(username.getText()));
                        loader.setController(controller);
                        Parent root= null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene scene = new Scene(root);
                        newWindow.setTitle("Course Attendance");
                        newWindow.setScene(scene);
                        newWindow.show();
                    }

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setContentText("Password không chinh xác!");
                    alert.showAndWait();
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setContentText("Dữ liệu nhập không hợp lệ!");
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public boolean isInputValid(){
        if (username.getText().isEmpty()){
            return false;
        }
        else if (password.getText().isEmpty()){
            return false;
        }
        return true;
    }
}
