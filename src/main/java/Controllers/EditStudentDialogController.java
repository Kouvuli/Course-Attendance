package Controllers;

import DAO.ScheduleDAO;
import DAO.StudentDAO;
import Entities.Schedule;
import Entities.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class EditStudentDialogController implements Initializable {
    @FXML
    private TextField emailTxt;

    @FXML
    private TextField phoneNumberTxt;

    @FXML
    private DatePicker studentBirthday;

    @FXML
    private TextField studentCMNDTxt;

    @FXML
    private TextField studentNameTxt;

    private int id;
    private String name;
    private String cmnd;
    private Date birthday;
    private int phone;
    private String email;

    private Student oldStudent;
    @FXML
    void cancelHandler(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void confirmHandler(ActionEvent event) {
        StudentDAO studentDAO = new StudentDAO();
        Student newStudent=new Student(id,studentNameTxt.getText(),studentCMNDTxt.getText(),phoneNumberTxt.getText() ,Date.from(studentBirthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),emailTxt.getText());
        studentDAO.updateData(oldStudent,newStudent);
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate a= Instant.ofEpochMilli(birthday.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

        studentBirthday.setValue(a);
        studentNameTxt.setText(oldStudent.getName());
        studentCMNDTxt.setText(oldStudent.getCmnd());
        emailTxt.setText(oldStudent.getEmail());
        phoneNumberTxt.setText(String.valueOf(oldStudent.getPhone()));
    }

    public void setValue(Student student) {
        oldStudent=student;
        this.id = student.getId();
        this.name = student.getName();
        this.birthday = student.getBirthday();
        this.cmnd = student.getCmnd();
        this.email = student.getEmail();
        this.phone = student.getPhone();
    }
}
