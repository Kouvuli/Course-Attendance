package Controllers;

import DAO.CourseDAO;
import DAO.ScheduleDAO;
import DAO.TeacherDAO;
import Entities.Course;
import Entities.Schedule;
import Entities.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class NewScheduleDialogController implements Initializable {

    @FXML
    private TextField teacherIdTxt;
    @FXML
    private TextField courseIdTxt;
    @FXML
    private DatePicker dateEndPicker;

    @FXML
    private DatePicker dateStartPicker;

    @FXML
    private TextField dayOfWeekTxt;


    @FXML
    private TextField roomTxt;

    @FXML
    private TextField shiftEndTxt;

    @FXML
    private TextField shiftStartTxt;

    @FXML
    private TextField termTxt;

    @FXML
    private TextField yearTxt;

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
        }
        else {
            CourseDAO courseDAO=new CourseDAO();
            TeacherDAO teacherDAO=new TeacherDAO();

            ScheduleDAO scheduleDAO = new ScheduleDAO();
            Course a=null;
            Teacher b=null;
            try {
                a=courseDAO.getCourseById(courseIdTxt.getText());
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setContentText("Id lớp học không tồn tại!");
                alert.showAndWait();
            }
            try{
                b=teacherDAO.getTeacherById(Integer.parseInt(teacherIdTxt.getText()));
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setContentText("Id giáo viên không tồn tại!");
                alert.showAndWait();
            }
            if(a!=null&&b!=null){
                Schedule c=null;
                try{
                    c=scheduleDAO.isAlreadyExist(courseIdTxt.getText(),Integer.parseInt(teacherIdTxt.getText()),dayOfWeekTxt.getText(),shiftStartTxt.getText(),shiftEndTxt.getText(),termTxt.getText(),yearTxt.getText());
                }catch (Exception e){

                }

                if(c==null){
                    Schedule newSchedule = new Schedule(a, b, Date.from(dateStartPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(dateEndPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), dayOfWeekTxt.getText(), shiftStartTxt.getText(), shiftEndTxt.getText(), roomTxt.getText(), termTxt.getText(), yearTxt.getText());
                    scheduleDAO.addData(newSchedule);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.close();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setContentText("Lịch đã tồn tại!");
                    alert.showAndWait();
                }

            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public boolean isInputValid(){
        if(courseIdTxt.getText().isEmpty()){
            return false;
        }
        else if(teacherIdTxt.getText().isEmpty()){
            return false;
        }
        else if (dayOfWeekTxt.getText().isEmpty()){
            return false;
        }
        else if(termTxt.getText().isEmpty()){
            return false;
        }
        else if(shiftEndTxt.getText().isEmpty()){
            return false;

        }
        else if (shiftStartTxt.getText().isEmpty()){
            return false;
        }
        else if (roomTxt.getText().isEmpty()){
            return false;
        }
        else if (yearTxt.getText().isEmpty()){
            return false;
        }
        return true;
    }


}
