package Controllers;

import DAO.CourseDAO;
import DAO.ScheduleDAO;
import Entities.Schedule;
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
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TeacherScheduleController implements Initializable {
//    @FXML
//    private TableView<ScheduleRow> scheduleTable;
//
//    @FXML
//    private TableColumn<ScheduleRow, Button> attendanceCol;
//
//    @FXML
//    private TableColumn<ScheduleRow,Date> dateEndCol;
//
//    @FXML
//    private TableColumn<ScheduleRow, Date> dateStartCol;
//
//    @FXML
//    private TableColumn<ScheduleRow, String> dayOfWeekCol;
//
//    @FXML
//    private TableColumn<ScheduleRow, Integer> idCol;
//
//    @FXML
//    private TableColumn<ScheduleRow, String> nameCol;
//
//    @FXML
//    private TableColumn<ScheduleRow, String> roomCol;
//
//    @FXML
//    private TableColumn<ScheduleRow, String> timeEndCol;
//
//    @FXML
//    private TableColumn<ScheduleRow, String> timeStartCol;

    @FXML
    private ComboBox<String> dayOfWeekCBox;
    @FXML
    private ComboBox<String> termCBox;

    @FXML
    private ComboBox<String> yearCBox;

    @FXML
    private TextField perPage;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField searchField;
    @FXML
    private Button addBtn;
    private int currPage=1;
    private int totalPage;
    private int perPageNum;
    private String term;
    private String year;
    private String dayOfWeek;
    private List<ScheduleRow> scheduleRows;
    private TableView<ScheduleRow> tableView;
    private List<String> termList;
    private List<String> dayOfWeekList;
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
        ScheduleDAO dao = new ScheduleDAO();
        yearCBox.setItems(dao.getAllYear());
        yearCBox.getSelectionModel().selectFirst();
        year=yearCBox.getValue();
        perPageNum=Integer.parseInt(perPage.getText());
        totalPage = dao.getTotalPage(perPageNum,term,year,dayOfWeek);
        perPage.textProperty().addListener((observableValue, s, t1) -> {
//                pagination.setCurrentPageIndex(0);
//                perPageNum=Integer.parseInt(t1);
//                totalPage = dao.getTotalPage(perPageNum);
//                if(totalPage!=-1) {
//                    pagination.setPageCount(totalPage);
//                    pagination.setPageFactory(integer -> {
//                        currPage = integer;
//                        return createPage(currPage, perPageNum);
//
//                    });
//                }
            perPageNum=Integer.parseInt(t1);

        });
        if(totalPage!=-1){
            pagination.setCurrentPageIndex(0);
            pagination.setPageCount(totalPage);
            pagination.setPageFactory(integer -> {
                currPage = integer;
                return createPage(currPage, perPageNum,term,year,dayOfWeek);

            });



        }

    }


    public void refreshTable(){
        pagination.setCurrentPageIndex(0);
        ScheduleDAO dao=new ScheduleDAO();
        totalPage = dao.getTotalPage(perPageNum,term,year,dayOfWeek);
        if(totalPage!=-1) {
            pagination.setPageCount(totalPage);
            pagination.setPageFactory(integer -> {
                currPage = integer;
                return createPage(currPage, perPageNum,term,year,dayOfWeek);

            });
        }
    }
    public TableView<ScheduleRow> createTable() {

        TableView<ScheduleRow> table = new TableView<>();

        TableColumn<ScheduleRow, Integer> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Integer>("id"));
        idCol.setPrefWidth(52);

        TableColumn<ScheduleRow, String> nameCol = new TableColumn<>("Môn học");
        nameCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("name"));
        nameCol.setPrefWidth(288);
        TableColumn<ScheduleRow, Date> dateStartCol = new TableColumn<>("Ngày bắt đầu");
        dateStartCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Date>("dateStart"));
        dateStartCol.setPrefWidth(115);

        TableColumn<ScheduleRow, Date> dateEndCol = new TableColumn<>("Ngày kết thúc");
        dateEndCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Date>("dateEnd"));
        dateEndCol.setPrefWidth(111);
        TableColumn<ScheduleRow, String> dayOfWeekCol = new TableColumn<>("Thứ");
        dayOfWeekCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("dayOfWeek"));
        dayOfWeekCol.setPrefWidth(66);
        TableColumn<ScheduleRow, String> timeStartCol = new TableColumn<>("Ca bắt đầu");
        timeStartCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("shiftStart"));
        timeStartCol.setPrefWidth(70);
        TableColumn<ScheduleRow, String> timeEndCol = new TableColumn<>("Ca kết thúc");
        timeEndCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("shiftEnd"));
        timeEndCol.setPrefWidth(70);
        TableColumn<ScheduleRow, String> roomCol = new TableColumn<>("Phòng");
        roomCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("room"));
        TableColumn<ScheduleRow, String> termCol = new TableColumn<>("Học kỳ");
        termCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("term"));
        termCol.setPrefWidth(70);
        TableColumn<ScheduleRow, String> yearCol = new TableColumn<>("Năm học");
        yearCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, String>("year"));
        yearCol.setPrefWidth(100);
        TableColumn<ScheduleRow, Button> attendancesBtnCol = new TableColumn<>("Điểm danh");
        attendancesBtnCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Button>("attendancesBtn"));
        attendancesBtnCol.setPrefWidth(100);
        TableColumn<ScheduleRow, Button> editCol = new TableColumn<>("Sửa");
        editCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Button>("editBtn"));
        editCol.setPrefWidth(100);
        TableColumn<ScheduleRow, Button> deleteCol = new TableColumn<>("Xóa");
        deleteCol.setCellValueFactory(new PropertyValueFactory<ScheduleRow, Button>("deleteBtn"));
        deleteCol.setPrefWidth(100);
        table.getColumns().addAll(idCol, nameCol, dateStartCol,dateEndCol,dayOfWeekCol,timeStartCol,timeEndCol,roomCol,termCol,yearCol,attendancesBtnCol,editCol,deleteCol);

        return table;
    }
    public TableView<ScheduleRow> createPage(int pageNum, int perPage,String term,String year,String dayOfWeek) {
        scheduleRows.clear();
        ScheduleDAO dao = new ScheduleDAO();

        TableView<ScheduleRow> view = createTable();
        ObservableList<Schedule> resultList = dao.getPagination(pageNum, perPage,term,year,dayOfWeek);

        resultList.forEach(i -> {
            Image img1 = new Image("/images/pencil.png");
            Image img2 = new Image("/images/XCircle.png");
            Image img3=new Image("/images/ClipboardListOutline.png");
            ImageView imageView1 = new ImageView(img1);
            ImageView imageView2 = new ImageView(img2);
            ImageView imageView3=new ImageView(img3);
            imageView1.setFitHeight(14);
            imageView1.setPreserveRatio(true);
            imageView2.setFitHeight(14);
            imageView2.setPreserveRatio(true);
            imageView3.setFitHeight(14);
            imageView3.setPreserveRatio(true);

            Button editBtn=new Button();
            addEditBtnHandler(editBtn,i);
            editBtn.setGraphic(imageView1);

            Button deleteBtn=new Button();
            deleteBtn.setGraphic(imageView2);
            addDeleteBtnHandler(deleteBtn,i);

            Button attendancesBtn=new Button();
            attendancesBtn.setGraphic((imageView3));
            addAttendanceBtnHandler(attendancesBtn,i);
            ScheduleRow r = new ScheduleRow(i.getId(), i.getDateStart(),
                    i.getDateEnd(), i.getDayOfWeek(), i.getShiftStart(),
                    i.getShiftEnd(), i.getRoom(), i.getTeacher(), i.getCourse(),
                    i.getCourse().getName(),i.getTerm(),i.getYear(),attendancesBtn,editBtn,deleteBtn);
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
        sortedData.comparatorProperty().bind(view.comparatorProperty());
        view.setItems(sortedData);
        return view;
    }

    private void addDeleteBtnHandler(Button deleteBtn, Schedule schedule) {
        deleteBtn.setOnAction(event -> {
            ScheduleDAO dao=new ScheduleDAO();
            dao.delData(schedule);

        });
    }

    private void addAttendanceBtnHandler(Button attendancesBtn, Schedule i) {

    }

    private void addEditBtnHandler(Button editBtn, Schedule schedule) {
        editBtn.setOnAction(event -> {
            Stage window = new Stage();

            window.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/layouts/edit-schedule-dialog.fxml"));
            EditScheduleDialogController controller=new EditScheduleDialogController();
            controller.setValue(schedule);
            loader.setController(controller);

            Parent root= null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene editScene = new Scene(root);
            window.setTitle("Sửa thời khóa biểu");
            window.setScene(editScene);
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
        refreshTable();
    }

    public void dayOfWeekHandler(ActionEvent event) {
        dayOfWeek=dayOfWeekCBox.getValue();
    }

    public void addHandler(ActionEvent event) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/layouts/new-schedule-dialog.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene editScene = new Scene(root);
        window.setTitle("Thêm thời khóa biểu");
        window.setScene(editScene);
        window.show();

    }
}
