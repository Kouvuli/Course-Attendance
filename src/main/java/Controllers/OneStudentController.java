package Controllers;

import DAO.ScheduleDAO;
import DAO.StudentDAO;
import Entities.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Date;

public class OneStudentController {
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

    private int scheduleId;
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
            StudentDAO studentDAO=new StudentDAO();
            Student student=null;
            try {
                student=studentDAO.getStudentById(Integer.parseInt(studentIdTxt.getText()));
            }catch (Exception e){
                Student newStudent = new Student(Integer.parseInt(studentIdTxt.getText()), studentNameTxt.getText(), studentCMNDTxt.getText(), Integer.parseInt(phoneNumberTxt.getText()), Date.from(studentBirthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), emailTxt.getText());
                ScheduleDAO scheduleDAO=new ScheduleDAO();
                scheduleDAO.updateStudentSet(scheduleId,newStudent);
                Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
                window.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setContentText("Thêm thành công");
                alert.showAndWait();

            }
            if(student!=null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setContentText("ID đã tồn tại!");
                alert.showAndWait();
            }
        }


    }
    public void setValue(int id){
        scheduleId=id;
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
