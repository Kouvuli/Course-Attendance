package Controllers;

import DAO.CourseDAO;
import Entities.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCourseDialogController implements Initializable {


    @FXML
    private TextField courseName;

    private Course oldCourse;
    private String id;
    private String name;
    public EditCourseDialogController(){}
    @FXML
    void cancelHandler(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        courseName.setText(name);
        oldCourse=new Course(id,name);

    }

    @FXML
    void confirmHandler(ActionEvent event) {
        CourseDAO dao = new CourseDAO();
        Course newCourse=new Course(id,courseName.getText());
        dao.updateData(oldCourse,newCourse);
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void setValue(String id,String name){
//        courseId=new TextField();
//        courseName=new TextField();
        this.id=id;
        this.name=name;

    }
}
