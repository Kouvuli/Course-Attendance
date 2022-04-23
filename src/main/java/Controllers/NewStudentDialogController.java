package Controllers;

import DAO.CourseDAO;
import DAO.ScheduleDAO;
import DAO.StudentDAO;
import DAO.TeacherDAO;
import Entities.Course;
import Entities.Schedule;
import Entities.Student;
import Entities.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class NewStudentDialogController implements Initializable {
    @FXML
    private TextField emailTxt;

    @FXML
    private TextField phoneNumberTxt;

    @FXML
    private DatePicker studentBirthday;

    @FXML
    private TextField studentCMNDTxt;

    @FXML
    private TextField studentIdTxt;

    @FXML
    private TextField studentNameTxt;


    @FXML
    void cancelHandler(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void confirmHandler(ActionEvent event) {
        if(!isInputValid()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setContentText("Dữ liệu nhập không hợp lệ!");
            alert.showAndWait();
        }else{

            Student student=null;
            StudentDAO studentDAO = new StudentDAO();
            try {
                student=studentDAO.getStudentById(Integer.parseInt(studentIdTxt.getText()));
            }catch (Exception exception){
                Student newStudent = new Student(Integer.parseInt(studentIdTxt.getText()), studentNameTxt.getText(), studentCMNDTxt.getText(), Integer.parseInt(phoneNumberTxt.getText()),Date.from(studentBirthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), emailTxt.getText());
                studentDAO.addData(newStudent);
                Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
                window.close();
            }
            if (student!=null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setContentText("ID đã tồn tại!");
                alert.showAndWait();
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public boolean isInputValid(){
        if(studentIdTxt.getText().isEmpty()){
            return false;
        }
        else if(studentNameTxt.getText().isEmpty()){
            return false;
        }
        else if (studentBirthday.getValue()==null){
            return false;
        }
        else if(phoneNumberTxt.getText().isEmpty()){
            return false;
        }
        else if(emailTxt.getText().isEmpty()){
            return false;

        }else{

            return true;
        }
    }
}
