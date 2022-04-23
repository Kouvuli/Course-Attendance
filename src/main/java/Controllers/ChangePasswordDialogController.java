package Controllers;

import DAO.StudentDAO;
import Entities.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordDialogController implements Initializable {
    @FXML
    private TextField password;

    @FXML
    private TextField retypePassword;

    private Student oldStudent;
    @FXML
    void handleCancleButton(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void applyHandler(ActionEvent event) {
        StudentDAO studentDAO = new StudentDAO();
        if(isValidInput()){
            if(password.getText().trim().equals(retypePassword.getText().trim())){
                Student newStudent=new Student(oldStudent.getId(),oldStudent.getName(),oldStudent.getCmnd(),oldStudent.getPhone(),oldStudent.getBirthday(),oldStudent.getEmail(),oldStudent.getUsername(),password.getText(),false);
                studentDAO.updateData(oldStudent,newStudent);
                Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
                window.close();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setContentText("Mật khẩu nhập lại không khớp!");
                alert.showAndWait();
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
    public void setValue(Student oldStudent){
        this.oldStudent=oldStudent;
    }
    public boolean isValidInput(){
        if(password.getText().trim().isEmpty()){
            return false;
        }
        else if(retypePassword.getText().trim().isEmpty()){
            return false;
        }
        return true;
    }
}
