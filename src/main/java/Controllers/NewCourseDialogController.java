package Controllers;

import DAO.CourseDAO;
import Entities.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewCourseDialogController implements Initializable {
    @FXML
    private TextField courseId;

    @FXML
    private TextField courseName;

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
        else{
            CourseDAO courseDAO=new CourseDAO();
            Course course=null;
            try{
                course=courseDAO.getCourseById(courseId.getText());
            }catch (Exception e){

            }
            if (course==null){
                Course newCourse=new Course(courseId.getText(),courseName.getText());
                courseDAO.addData(newCourse);
                Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
                window.close();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setContentText("Môn học đã tồn tại!");
                alert.showAndWait();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public boolean isInputValid(){
        if(courseId.getText().isEmpty()){
            return false;
        }
        else if (courseName.getText().isEmpty()){
            return false;
        }
        return true;
    }
}
