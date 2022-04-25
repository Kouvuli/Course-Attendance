package Controllers;

import DAO.StudentDAO;
import Entities.Attendance;
import Entities.Schedule;
import Entities.Student;
import Models.AttendanceResultRow;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StudentAttendanceResultController implements Initializable {
    @FXML
    private TableView<AttendanceResultRow> attendanceTable;

    @FXML
    private TableColumn<AttendanceResultRow, String> scheduleNameCol;

    @FXML
    private TableColumn<AttendanceResultRow, String> dateCol;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<AttendanceResultRow, String> dayOfWeekCol;

    @FXML
    private TableColumn<AttendanceResultRow, String> shiftEndCol;

    @FXML
    private TableColumn<AttendanceResultRow, String> shiftStartCol;

    @FXML
    private TableColumn<AttendanceResultRow, String> statusCol;

    private int studentId;
    private LocalDate currDate;
    private Student currStudent;
    private LocalDate[] datesMark=new LocalDate[16];
    @FXML
    void watchHandler(ActionEvent event) {
        refreshTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.valueProperty().addListener((observableValue, localDate, t1) -> {
            currDate=t1;
        });
    }
    public void setValue(int id){
        studentId=id;

    }
    public void refreshTable(){

        StudentDAO studentDAO=new StudentDAO();
        currStudent = studentDAO.getStudentById(studentId);
        List<AttendanceResultRow>resultList=new ArrayList<>();
        for (Schedule schedule:currStudent.getSchedules()){
            if(isWithinRange(currDate,convertLocalDate(schedule.getDateStart()),convertLocalDate(schedule.getDateEnd()))){
                if (Integer.parseInt(schedule.getDayOfWeek()) == (convertDayOfWeek(currDate) + 1)) {
                    AttendanceResultRow r=new AttendanceResultRow(schedule.getId(), schedule.getCourse().getName(),convertDateToString("yyyy-MM-dd",convertDate(currDate)),schedule.getDayOfWeek(),schedule.getShiftStart(),schedule.getShiftEnd(),"Chưa điểm danh");

                    resultList.add(r);
                }
            }


        }
        for (AttendanceResultRow row:resultList) {
            for (Attendance attendance:currStudent.getAttendances()){
                if(attendance.getSchedule().getId()==row.getId()&&convertDate(currDate).compareTo(attendance.getDay())==0){
                    row.setStatus("Đã điểm danh");
                }
            }
        }
        scheduleNameCol.setCellValueFactory(new PropertyValueFactory<AttendanceResultRow,String>("scheduleName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<AttendanceResultRow,String>("date"));
        dayOfWeekCol.setCellValueFactory(new PropertyValueFactory<AttendanceResultRow,String>("dayOfWeek"));
        statusCol.setCellValueFactory(new PropertyValueFactory<AttendanceResultRow,String>("status"));
        shiftStartCol.setCellValueFactory(new PropertyValueFactory<AttendanceResultRow,String>("shiftStart"));
        shiftEndCol.setCellValueFactory(new PropertyValueFactory<AttendanceResultRow,String>("shiftEnd"));
        attendanceTable.getStyleClass().add("center-column");
        attendanceTable.setItems(FXCollections.observableArrayList(resultList));

    }
    public  String convertDateToString(String format,Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return  sdf.format(date);


    }
    boolean isWithinRange(LocalDate date,LocalDate startDate,LocalDate endDate) {
        Long a= ChronoUnit.DAYS.between(startDate, date);
        Long b = ChronoUnit.DAYS.between(date, endDate);
        if( a>=0&&b>=0){
            return true;
        }
        return false;
    }

    public Date convertDate(LocalDate date){
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    public LocalDate convertLocalDate(Date date){
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    public int convertDayOfWeek(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getValue();
    }
}
