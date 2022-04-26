package Controllers;

import DAO.StudentDAO;
import DAO.TeacherDAO;
import Entities.Student;
import Entities.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class VerifyUsernamePasswordController {

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;
    private Teacher teacher;
    private Student student;
    @FXML
    void cancelHandler(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void confirmHandler(ActionEvent event) {
        if (isValidInput()){
            StudentDAO studentDAO=new StudentDAO();
            TeacherDAO teacherDAO=new TeacherDAO();

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
                    newWindow.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/change-password-dialog.fxml"));
                    ChangePasswordDialogController controller=new ChangePasswordDialogController();
                    controller.setValue(Integer.parseInt(username.getText()));
                    loader.setController(controller);
                    Parent root= null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(root);
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
                    Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
                    window.close();
                    Stage newWindow = new Stage();
                    newWindow.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/change-password-dialog.fxml"));
                    ChangePasswordDialogController controller=new ChangePasswordDialogController();
                    controller.setValue(Integer.parseInt(username.getText()));
                    loader.setController(controller);
                    Parent root= null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(root);
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
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setContentText("Dữ liệu nhập không hợp lệ!");
            alert.showAndWait();
        }


    }
   public boolean isValidInput(){
        if(username.getText().trim().isEmpty()){
            return false;
        }
        else if(password.getText().trim().isEmpty()){
            return false;
        }
        return true;
   }
}
