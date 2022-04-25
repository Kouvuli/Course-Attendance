package Controllers;

import DAO.CourseDAO;
import DAO.ScheduleDAO;
import DAO.StudentDAO;
import Entities.Schedule;
import Entities.Student;
import Models.ScheduleRow;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.util.*;

public class StudentScheduleController implements Initializable {

    @FXML
    private TableColumn<ScheduleRow, Button> attendancesCol;
    @FXML
    private TableColumn<ScheduleRow, Date> dateEndCol;

    @FXML
    private TableColumn<ScheduleRow, Date> dateStartCol;

    @FXML
    private TableColumn<ScheduleRow, String> dayOfWeekCol;

    @FXML
    private ComboBox<String> dayOfWeekCBox;

    @FXML
    private TableColumn<ScheduleRow, Integer> idCol;

    @FXML
    private TableColumn<ScheduleRow, String> nameCol;

    @FXML
    private TextField perPage;

    @FXML
    private TableColumn<ScheduleRow, String> roomCol;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<ScheduleRow, String> shiftEndCol;

    @FXML
    private TableColumn<ScheduleRow, String> shiftStartCol;

    @FXML
    private TableView<ScheduleRow> tableView;

    @FXML
    private TableColumn<ScheduleRow, String> termCol;

    @FXML
    private ComboBox<String> termCBox;

    @FXML
    private TableColumn<ScheduleRow, String> yearCol;

    @FXML
    private ComboBox<String> yearCBox;

    private int studentId;
//    private int currPage=1;
//    private int totalPage;
//    private int perPageNum;
    private String term;
    private String year;
    private String dayOfWeek;
    private List<ScheduleRow> scheduleRows;
    private List<String> termList;
    private List<String> dayOfWeekList;
    private Student currStudent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scheduleRows = new ArrayList<>();
        termList=new ArrayList<>(Arrays.asList("1","2"));
        termCBox.setItems(FXCollections.observableArrayList(termList));
        termCBox.getSelectionModel().selectFirst();
        term=termCBox.getValue();
        dayOfWeekList=new ArrayList<>(
                Arrays.asList("2", "3", "4","5","6","7"));
        dayOfWeekCBox.setItems(FXCollections.observableArrayList(dayOfWeekList));
        dayOfWeekCBox.getSelectionModel().selectFirst();
        dayOfWeek=dayOfWeekCBox.getValue();
        StudentDAO studentDAO=new StudentDAO();
        currStudent=studentDAO.getStudentById(studentId);

        List<String> yearList=new ArrayList<>();
        for (Schedule schedule : currStudent.getSchedules()) {
            yearList.add(schedule.getYear());
        }
        Set<String> yearList2=new HashSet<>(yearList);
        yearCBox.setItems(FXCollections.observableArrayList(yearList2));
        yearCBox.getSelectionModel().selectFirst();
        year=yearCBox.getValue();
        idCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("name"));
        dateStartCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Date>("dateStart"));
        dateEndCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Date>("dateEnd"));
        dayOfWeekCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("dayOfWeek"));
        shiftStartCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("shiftStart"));
        shiftEndCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("shiftEnd"));
        roomCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("room"));
        yearCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("year"));
        termCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("term"));
        attendancesCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Button>("attendancesBtn"));
//        perPageNum=Integer.parseInt(perPage.getText());
//        totalPage = dao.getTotalPage(perPageNum,term,year,dayOfWeek);
//        perPage.textProperty().addListener((observableValue, s, t1) -> {
//            perPageNum=Integer.parseInt(t1);
//        });
//        if(totalPage!=-1){
//            pagination.setCurrentPageIndex(0);
//            pagination.setPageCount(totalPage);
//            pagination.setPageFactory(integer -> {
//                currPage = integer;
//                return createPage(currPage, perPageNum,term,year,dayOfWeek);
//
//            });
//        }

    }



//    public TableView<ScheduleRow> createTable() {
//
//        TableView<ScheduleRow> table = new TableView<>();
//
//        TableColumn<ScheduleRow, Integer> idCol = new TableColumn<>("Id");
//        idCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Integer>("id"));
//        idCol.setPrefWidth(52);
//
//        TableColumn<ScheduleRow, String> nameCol = new TableColumn<>("Môn học");
//        nameCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("name"));
//        nameCol.setPrefWidth(288);
//        TableColumn<ScheduleRow, Date> dateStartCol = new TableColumn<>("Ngày bắt đầu");
//        dateStartCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Date>("dateStart"));
//        dateStartCol.setPrefWidth(115);
//
//        TableColumn<ScheduleRow, Date> dateEndCol = new TableColumn<>("Ngày kết thúc");
//        dateEndCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Date>("dateEnd"));
//        dateEndCol.setPrefWidth(111);
//        TableColumn<ScheduleRow, String> dayOfWeekCol = new TableColumn<>("Thứ");
//        dayOfWeekCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("dayOfWeek"));
//        dayOfWeekCol.setPrefWidth(66);
//        TableColumn<ScheduleRow, String> timeStartCol = new TableColumn<>("Ca bắt đầu");
//        timeStartCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("shiftStart"));
//        timeStartCol.setPrefWidth(70);
//        TableColumn<ScheduleRow, String> timeEndCol = new TableColumn<>("Ca kết thúc");
//        timeEndCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("shiftEnd"));
//        timeEndCol.setPrefWidth(70);
//        TableColumn<ScheduleRow, String> roomCol = new TableColumn<>("Phòng");
//        roomCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("room"));
//        TableColumn<ScheduleRow, String> termCol = new TableColumn<>("Học kỳ");
//        termCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("term"));
//        termCol.setPrefWidth(70);
//        TableColumn<ScheduleRow, String> yearCol = new TableColumn<>("Năm học");
//        yearCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("year"));
//        yearCol.setPrefWidth(100);
//        table.getColumns().addAll(idCol, nameCol, dateStartCol,dateEndCol,dayOfWeekCol,timeStartCol,timeEndCol,roomCol,termCol,yearCol);
//
//        return table;
//    }

    public ObservableList<Schedule> tableFilter(List<Schedule> a, String term, String year, String dayOfWeek) {
        List<Schedule> resultList=new ArrayList<>();
        for (Schedule s : a) {
            if(s.getDayOfWeek().equals(dayOfWeek)&&s.getYear().equals(year)&&s.getTerm().equals(term)){
                resultList.add(s);
            }
        }
        return FXCollections.observableArrayList(resultList);
    }
    public void refreshTable(String term,String year,String dayOfWeek) {
        scheduleRows.clear();
        StudentDAO studentDAO=new StudentDAO();
        currStudent=studentDAO.getStudentById(studentId);
        List<Schedule> scheduleList = new ArrayList<>(currStudent.getSchedules());

//        ObservableList<Schedule> resultList = dao.getPagination(pageNum, perPage,term,year,dayOfWeek);
        ObservableList<Schedule> resultList=tableFilter(scheduleList,term,year,dayOfWeek);
        resultList.forEach(i -> {
            Image img = new Image("/images/ClipboardListOutline.png");
            ImageView imageView=new ImageView(img);
            imageView.setFitHeight(14);
            imageView.setPreserveRatio(true);
            Button attendancesBtn=new Button();
            attendancesBtn.setGraphic((imageView));
            addAttendanceBtnHandler(attendancesBtn,i);
            ScheduleRow r = new ScheduleRow(i.getId(), i.getDateStart(),
                    i.getDateEnd(), i.getDayOfWeek(), i.getShiftStart(),
                    i.getShiftEnd(), i.getRoom(), i.getTeacher(), i.getCourse(),
                    i.getCourse().getName(),i.getTerm(),i.getYear(),attendancesBtn);
            scheduleRows.add(r);

        });

        FilteredList<ScheduleRow> filteredListData = new FilteredList<>(FXCollections.observableArrayList(scheduleRows), b -> true);
        searchField.textProperty().addListener((observableValue, s, t1) -> {
            filteredListData.setPredicate(scheduleRow -> {
                if (t1==null||t1.isEmpty()){
                    return true;
                }
                String lowerCaseFilter=t1.toLowerCase();
                if(scheduleRow.getName().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<ScheduleRow> sortedData = new SortedList<>(filteredListData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.getStyleClass().add("center-column");
        tableView.setItems(sortedData);
    }
    private void addAttendanceBtnHandler(Button attendancesBtn, Schedule schedule) {
        attendancesBtn.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/student-attendance-view.fxml"));
            StudentAttendanceController controller = new StudentAttendanceController();
            controller.setValue(studentId,schedule.getId(), schedule.getDateStart(),schedule.getShiftStart(),schedule.getShiftEnd(),schedule.getAttendances());
            loader.setController(controller);
            Stage window=new Stage();
            Parent root= null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene=new Scene(root);
            window.setTitle("Điểm danh");
            window.setScene(scene);
            window.show();
        });
    }
    public void termCBoxHandler(ActionEvent event) {
        term=termCBox.getValue();
    }

    public void yearCBoxHandler(ActionEvent event) {

        year=yearCBox.getValue();
    }

    public void applyHandler(ActionEvent event) {

        refreshTable(term,year,dayOfWeek);
    }

    public void dayOfWeekHandler(ActionEvent event) {
        dayOfWeek=dayOfWeekCBox.getValue();
    }
    public void setValue(int id){
        this.studentId=id;

    }
}
