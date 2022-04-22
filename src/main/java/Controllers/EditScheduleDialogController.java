package Controllers;

import DAO.CourseDAO;
import DAO.ScheduleDAO;
import Entities.Course;
import Entities.Schedule;
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

public class EditScheduleDialogController implements Initializable {
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

    int id;
    Date dateStart;
    Date dateEnd;
    String dayOfWeek;
    String shiftStart;
    String shiftEnd;
    String room;
    String term;
    String year;
    Schedule oldSchedule;
    @FXML
    void cancelHandler(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
    @FXML
    void confirmHandler(ActionEvent event) {
        ScheduleDAO dao = new ScheduleDAO();
        Schedule newSchedule=new Schedule(id,Date.from(dateStartPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),Date.from(dateEndPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),dayOfWeekTxt.getText(), shiftStartTxt.getText(), shiftEndTxt.getText(), roomTxt.getText(), termTxt.getText(), yearTxt.getText());
        dao.updateData(oldSchedule,newSchedule);
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalDate a= Instant.ofEpochMilli(dateStart.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate b= Instant.ofEpochMilli(dateEnd.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        dateStartPicker.setValue(a);
        dateEndPicker.setValue(b);
        dayOfWeekTxt.setText(oldSchedule.getDayOfWeek());
        shiftStartTxt.setText(oldSchedule.getShiftStart());
        shiftEndTxt.setText(oldSchedule.getShiftEnd());
        roomTxt.setText(oldSchedule.getRoom());
        termTxt.setText(oldSchedule.getTerm());
        yearTxt.setText(oldSchedule.getYear());

    }

    public void setValue(Schedule schedule) {
        oldSchedule=schedule;
        this.id = schedule.getId();
        this.dateStart = schedule.getDateStart();
        this.dateEnd = schedule.getDateEnd();
        this.dayOfWeek = schedule.getDayOfWeek();
        this.shiftEnd = schedule.getShiftEnd();
        this.shiftStart = schedule.getShiftStart();
        this.room = schedule.getRoom();
        this.term = schedule.getTerm();
        this.year = schedule.getYear();
    }
}
