package Controllers;

import DAO.AttendanceDAO;
import DAO.ScheduleDAO;
import DAO.StudentDAO;
import Entities.Attendance;
import Entities.Schedule;
import Entities.Student;
import Models.AttendanceRow;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import org.hibernate.annotations.Check;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TeacherAttendanceController implements Initializable {
    @FXML
    private TableColumn<AttendanceRow, Integer> idCol;
    @FXML
    private TableView<AttendanceRow> attendanceTable;
    @FXML
    private TableColumn<AttendanceRow, String> nameCol;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week10Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week11Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week12Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week13Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week14Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week15Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week1Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week2Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week3Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week4Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week5Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week6Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week7Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week8Col;

    @FXML
    private TableColumn<AttendanceRow, CheckBox> week9Col;

    private int scheduleId;
    private Date dateStart;
    private LocalDate[] datesMark=new LocalDate[16];
    private Set<Attendance>attendances;
    public TeacherAttendanceController() {
    }

    @FXML
    void applyHandler(ActionEvent event) {
        ScheduleDAO scheduleDAO=new ScheduleDAO();
        AttendanceDAO attendanceDAO=new AttendanceDAO();
        StudentDAO studentDAO=new StudentDAO();
        Schedule schedule=scheduleDAO.getScheduleById(scheduleId);
        int dayOfWeek=Integer.parseInt(schedule.getDayOfWeek());
        for (Attendance a:attendances){
            attendanceDAO.delData(a);
        }
        for(AttendanceRow row:attendanceTable.getItems()){
            List<Date> dateList=new ArrayList<>();
            for(int i=0;i<row.getWeeks().length;i++){
                if(row.getWeeks()[i].isSelected()){
                    for (LocalDate date = datesMark[i]; date.isBefore(datesMark[i+1]); date = date.plusDays(1))
                    {
                        if(getDayOfWeek(date)==dayOfWeek-1 ){
                            Student student=studentDAO.getStudentById(row.getId());
                            Attendance newAttendance=new Attendance(Date.from(date.atStartOfDay()
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant()),schedule,student);
                            attendanceDAO.addData(newAttendance);
                        }
                    }
                }
            }
        }
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
    public int getDayOfWeek(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getValue();
    }
    @FXML
    void cancelHandler(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void watchHandler(ActionEvent event) {
        refreshTable();
    }
    public void refreshTable(){
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        Schedule schedule=scheduleDAO.getScheduleById(scheduleId);

        List<AttendanceRow> attendanceRowList=new ArrayList<>();

        for(Student student:schedule.getStudents()){
            CheckBox[] weeks=new CheckBox[15];
            for (int i=0;i<weeks.length;i++){
                weeks[i]=new CheckBox();
            }
            AttendanceRow r=new AttendanceRow(student.getId(),student.getName(),weeks);
            attendanceRowList.add(r);
        }
        for (Attendance a:schedule.getAttendances()) {
            Pair<Integer,AttendanceRow> temp = isContain(attendanceRowList,a.getStudent().getId());

            if(temp==null){
                CheckBox[] weeks=new CheckBox[15];
                for (int i=0;i<weeks.length;i++){
                    weeks[i]=new CheckBox();
                }
                AttendanceRow h=new AttendanceRow(a.getStudent().getId(),a.getStudent().getName(),weeks);
                LocalDate attendDay=Instant.ofEpochMilli(a.getDay().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                for(int i=0;i<datesMark.length-1;i++){
                    if(isWithinRange(attendDay,datesMark[i],datesMark[i+1])){
                        weeks[i].setSelected(true);
                        h.setWeeks(weeks);
                    }
                }
                attendanceRowList.add(h);

            }
            else{
                AttendanceRow h=temp.getValue();
                LocalDate attendDay=Instant.ofEpochMilli(a.getDay().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                for(int i=0;i<datesMark.length-1;i++){
                    if(isWithinRange(attendDay,datesMark[i],datesMark[i+1])){
                        CheckBox[] weeks=h.getWeeks();
                        weeks[i].setSelected(true);
                        h.setWeeks(weeks);

                    }
                }
                attendanceRowList.set(temp.getKey(),h);

            }


        }
        idCol.setCellValueFactory(new PropertyValueFactory<AttendanceRow, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<AttendanceRow, String>("name"));
        week1Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[0]));
        week2Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[1]));
        week3Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[2]));
        week4Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[3]));
        week5Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[4]));
        week6Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[5]));
        week7Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[6]));
        week8Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[7]));
        week9Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[8]));
        week10Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[9]));
        week11Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[10]));
        week12Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[11]));
        week13Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[12]));
        week14Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[13]));
        week15Col.setCellValueFactory(entry-> new SimpleObjectProperty<CheckBox>(entry.getValue().getWeeks()[14]));

        attendanceTable.setItems(FXCollections.observableArrayList(attendanceRowList));

    }

    public Pair<Integer,AttendanceRow> isContain(List<AttendanceRow> a, int id) {
        for (int i=0;i<a.size();i++) {
            if(a.get(i).getId()==id){
                return new Pair<Integer,AttendanceRow>(i,a.get(i));
            }
        }
        return null;
    }
    boolean isWithinRange(LocalDate date,LocalDate startDate,LocalDate endDate) {
        Long a= ChronoUnit.DAYS.between(startDate, date);
        Long b = ChronoUnit.DAYS.between(date, endDate);
        if( a>=0&&b>=0){
            return true;
        }
        return false;
    }
    public void setValue(int scheduleId,Date dateStart,Set<Attendance> attendances){
        this.scheduleId=scheduleId;
        this.dateStart=dateStart;
        this.attendances=attendances;
        datesMark[0]= Instant.ofEpochMilli(dateStart.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        for (int i = 1; i <= 15; i++) {
            datesMark[i]=datesMark[0].plus(7*i, ChronoUnit.DAYS);
        }
    }
}
