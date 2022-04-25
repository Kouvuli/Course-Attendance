package Controllers;

import DAO.CourseDAO;
import DAO.ScheduleDAO;
import Entities.Course;
import Entities.Schedule;
import Models.CourseRow;
import Models.ScheduleRow;
import Models.StudentRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TeacherCourseController implements Initializable {
    @FXML
    private Button applyBtn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField perPage;

    @FXML
    private TextField searchField;
    private int currPage;
    private int totalPage;
    private int perPageNum;

    private List<CourseRow> courseRows;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courseRows=new ArrayList<>();
        CourseDAO dao=new CourseDAO();
        perPageNum=Integer.parseInt(perPage.getText());
        totalPage = dao.getTotalPage(perPageNum);
        perPage.textProperty().addListener((observableValue, s, t1) -> {
            perPageNum=Integer.parseInt(t1);

        });
        if(totalPage!=-1){
            pagination.setCurrentPageIndex(0);
            pagination.setPageCount(totalPage);
            pagination.setPageFactory(integer -> {
                currPage = integer;
                return createPage(currPage, perPageNum);

            });

        }
    }

    public void refreshTable(){
        pagination.setCurrentPageIndex(0);
        CourseDAO dao=new CourseDAO();
        totalPage = dao.getTotalPage(perPageNum);
        if(totalPage!=-1) {
            pagination.setPageCount(totalPage);
            pagination.setPageFactory(integer -> {
                currPage = integer;
                return createPage(currPage, perPageNum);

            });
        }
    }

    public TableView<CourseRow> createTable() {

        TableView<CourseRow> table = new TableView<>();
        table.getStyleClass().add("center-column");
        TableColumn<CourseRow, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<CourseRow, String>("id"));
        idCol.setPrefWidth(100);

        TableColumn<CourseRow, String> nameCol = new TableColumn<>("Tên môn học");
        nameCol.setCellValueFactory(new PropertyValueFactory<CourseRow, String>("name"));
        nameCol.setPrefWidth(330);


        TableColumn<CourseRow, Button> editBtnCol = new TableColumn<>("Sửa");
        editBtnCol.setCellValueFactory(new PropertyValueFactory<CourseRow, Button>("editBtn"));
        editBtnCol.setPrefWidth(100);
        TableColumn<CourseRow, Button> deleteBtnCol = new TableColumn<>("Xóa");
        deleteBtnCol.setCellValueFactory(new PropertyValueFactory<CourseRow, Button>("deleteBtn"));
        deleteBtnCol.setPrefWidth(100);
        table.getColumns().addAll(idCol, nameCol,editBtnCol,deleteBtnCol);

        return table;
    }
    public void addDeleteBtnHandler(Button deleteBtn, Course course){
        deleteBtn.setOnAction(event -> {
            CourseDAO dao=new CourseDAO();
            dao.delData(course);

        });
    }
    public void addEditBtnHandler(Button editBtn,Course course){
        editBtn.setOnAction(event -> {
            Stage window = new Stage();

            window.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/layouts/edit-course-dialog.fxml"));
            EditCourseDialogController controller=new EditCourseDialogController();
            controller.setValue(course.getId(),course.getName());
            loader.setController(controller);

            Parent root= null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene editScene = new Scene(root);
            window.setTitle("Sửa môn học");
            window.setScene(editScene);
            window.show();
        });

    }
    public TableView<CourseRow> createPage(int pageNum, int perPage) {
        courseRows.clear();
        CourseDAO dao = new CourseDAO();

        TableView<CourseRow> view = createTable();
        ObservableList<Course> resultList = dao.getPagination(pageNum, perPage);

        resultList.forEach(i -> {
            Image img1 = new Image("/images/pencil.png");
            Image img2 = new Image("/images/XCircle.png");
            ImageView imageView1 = new ImageView(img1);
            ImageView imageView2 = new ImageView(img2);
            imageView1.setFitHeight(14);
            imageView1.setPreserveRatio(true);
            imageView2.setFitHeight(14);
            imageView2.setPreserveRatio(true);
            Button editBtn=new Button();
            addEditBtnHandler(editBtn,i);
            editBtn.setGraphic(imageView1);
            Button deleteBtn=new Button();
            deleteBtn.setGraphic(imageView2);
            addDeleteBtnHandler(deleteBtn,i);
            CourseRow r = new CourseRow(i.getId(),i.getName(),editBtn,deleteBtn);
            courseRows.add(r);
        });
        FilteredList<CourseRow> filteredListData = new FilteredList<>(FXCollections.observableArrayList(courseRows), b -> true);
        searchField.textProperty().addListener((observableValue, s, t1) -> {
            filteredListData.setPredicate(courseRow -> {
                if (t1==null||t1.isEmpty()){
                    return true;
                }
                String lowerCaseFilter=t1.toLowerCase();
                if(courseRow.getName().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true;
                }
                else if(courseRow.getId().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<CourseRow> sortedData = new SortedList<>(filteredListData);
        sortedData.comparatorProperty().bind(view.comparatorProperty());
        view.setItems(sortedData);
        return view;
    }
    @FXML
    void applyHandler(ActionEvent event) {
        refreshTable();
    }

    public void addHandler(ActionEvent event) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/layouts/new-course-dialog.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        window.setTitle("Thêm môn học");
        window.setScene(scene);
        window.show();
    }
}
