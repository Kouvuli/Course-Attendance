package Controllers;

import DAO.ScheduleDAO;
import DAO.StudentDAO;
import Entities.Student;
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

public class TeacherStudentController implements Initializable {
    @FXML
    private Pagination pagination;

    @FXML
    private TextField perPage;

    @FXML
    private TextField searchField;
    private int currPage;
    private int totalPage;
    private int perPageNum;

    private List<StudentRow> studentRows;
    @FXML
    void addHandler(ActionEvent event) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/layouts/new-student-dialog.fxml"));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        window.setTitle("Thêm sinh viên");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void applyHandler(ActionEvent event) {
        refreshTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentRows=new ArrayList<>();
        StudentDAO dao=new StudentDAO();
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

    public TableView<StudentRow> createPage(int pageNum, int perPage) {
        studentRows.clear();
        StudentDAO dao = new StudentDAO();

        TableView<StudentRow> view = createTable();
        ObservableList<Student> resultList = dao.getPagination(pageNum, perPage);

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
            StudentRow r = new StudentRow(i.getId(),i.getName(),i.getCmnd(),i.getBirthday(),i.getPhone(),i.getEmail(),i.getIsFirst(),i.getUsername(),i.getPassword(),editBtn,deleteBtn);
            studentRows.add(r);
        });
        FilteredList<StudentRow> filteredListData = new FilteredList<>(FXCollections.observableArrayList(studentRows), b -> true);
        searchField.textProperty().addListener((observableValue, s, t1) -> {
            filteredListData.setPredicate(studentRow -> {
                if (t1==null||t1.isEmpty()){
                    return true;
                }
                String lowerCaseFilter=t1.toLowerCase();
                if(studentRow.getName().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else if(studentRow.getEmail().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else if(studentRow.getUsername().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else if(studentRow.getCmnd().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<StudentRow> sortedData = new SortedList<>(filteredListData);
        sortedData.comparatorProperty().bind(view.comparatorProperty());
        view.setItems(sortedData);
        return view;
    }

    private void addEditBtnHandler(Button editBtn, Student student) {
        editBtn.setOnAction(event -> {
            Stage window = new Stage();

            window.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/layouts/edit-student-dialog.fxml"));
            EditStudentDialogController controller=new EditStudentDialogController();
            controller.setValue(student);
            loader.setController(controller);

            Parent root= null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene editScene = new Scene(root);
            window.setTitle("Sửa sinh viên");
            window.setScene(editScene);
            window.show();
        });
    }

    private void addDeleteBtnHandler(Button deleteBtn, Student student) {
        deleteBtn.setOnAction(event -> {
            StudentDAO dao=new StudentDAO();
            dao.delData(student);

        });
    }

    public void refreshTable(){
        pagination.setCurrentPageIndex(0);
        StudentDAO dao=new StudentDAO();
        totalPage = dao.getTotalPage(perPageNum);
        if(totalPage!=-1) {
            pagination.setPageCount(totalPage);
            pagination.setPageFactory(integer -> {
                currPage = integer;
                return createPage(currPage, perPageNum);

            });
        }
    }
    public TableView<StudentRow> createTable() {

        TableView<StudentRow> table = new TableView<>();
        table.getStyleClass().add("center-column");
        TableColumn<StudentRow, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<StudentRow, String>("id"));
        idCol.setPrefWidth(100);

        TableColumn<StudentRow, String> nameCol = new TableColumn<>("Tên");
        nameCol.setCellValueFactory(new PropertyValueFactory<StudentRow, String>("name"));
        nameCol.setPrefWidth(330);
        TableColumn<StudentRow, String> cmndCol = new TableColumn<>("CMND");
        cmndCol.setCellValueFactory(new PropertyValueFactory<StudentRow, String>("cmnd"));
        cmndCol.setPrefWidth(100);

        TableColumn<StudentRow, Date> birthdayCol = new TableColumn<>("Ngày sinh");
        birthdayCol.setCellValueFactory(new PropertyValueFactory<StudentRow, Date>("birthday"));
        birthdayCol.setPrefWidth(100);

        TableColumn<StudentRow, Integer> phoneCol = new TableColumn<>("Số điện thoại");
        phoneCol.setCellValueFactory(new PropertyValueFactory<StudentRow, Integer>("phone"));
        phoneCol.setPrefWidth(100);


        TableColumn<StudentRow, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<StudentRow, String>("email"));
        emailCol.setPrefWidth(100);
        TableColumn<StudentRow, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<StudentRow, String>("username"));
        usernameCol.setPrefWidth(100);
        TableColumn<StudentRow, String> passwordCol = new TableColumn<>("Mật khẩu");
        passwordCol.setCellValueFactory(new PropertyValueFactory<StudentRow, String>("password"));
        passwordCol.setPrefWidth(100);
        TableColumn<StudentRow, Button> editBtnCol = new TableColumn<>("Sửa");
        editBtnCol.setCellValueFactory(new PropertyValueFactory<StudentRow, Button>("editBtn"));
        editBtnCol.setPrefWidth(100);
        TableColumn<StudentRow, Button> deleteBtnCol = new TableColumn<>("Xóa");
        deleteBtnCol.setCellValueFactory(new PropertyValueFactory<StudentRow, Button>("deleteBtn"));
        deleteBtnCol.setPrefWidth(100);
        table.getColumns().addAll(idCol, nameCol,cmndCol,birthdayCol,phoneCol,emailCol,usernameCol,passwordCol,editBtnCol,deleteBtnCol);

        return table;
    }
}
