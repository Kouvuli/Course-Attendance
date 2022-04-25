package Controllers;

import DAO.ScheduleDAO;
import DAO.StudentDAO;
import Entities.Schedule;
import Entities.Student;
import Models.ListStudentRow;
import Utils.HibernateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Check;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class ListStudentController implements Initializable {

    @FXML
    private TableView<ListStudentRow> tableView;

    @FXML
    private TableColumn<ListStudentRow, CheckBox> checkBoxCol;

    @FXML
    private TableColumn<ListStudentRow, Integer> studentIdCol;

    @FXML
    private TableColumn<ListStudentRow, String> studentNameCol;

    @FXML
    void applyHandler(ActionEvent event) {
        ScheduleDAO scheduleDAO=new ScheduleDAO();
//        Schedule schedule = scheduleDAO.getScheduleById(scheduleId);
//        Session session= HibernateUtils.getFACTORY().openSession();
//        Transaction transaction=session.beginTransaction();
        for (ListStudentRow r:tableView.getItems()){
            if(r.getCheckBox().isSelected()){
                scheduleDAO.updateStudentSet(scheduleId,r.getStudent());
            }
//            session.persist(r.getStudent());
        }
//        transaction.commit();
//        session.close();

        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setContentText("Thêm thành công");
        alert.showAndWait();

    }

    @FXML
    void cancelHandler(ActionEvent event) {
        Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
    private int scheduleId;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StudentDAO studentDAO=new StudentDAO();
        ObservableList<Student> list=studentDAO.getAll();
        List<Student>a=new ArrayList<>();
        ScheduleDAO scheduleDAO=new ScheduleDAO();
        List<ListStudentRow> resultList=new ArrayList<>();
        Schedule schedule = scheduleDAO.getScheduleById(scheduleId);
        for(int i=0;i<list.size();i++){
            boolean flag=false;
            for (Student student:schedule.getStudents()){
                if (student.getId() == list.get(i).getId()) {
                    flag=true;
                }
            }
            if (!flag){
                a.add(list.get(i));
            }
        }

        for (Student student:a){
            ListStudentRow r=new ListStudentRow(new CheckBox(),student.getId(),student.getName(),student);
            resultList.add(r);
        }

        checkBoxCol.setCellValueFactory(new PropertyValueFactory<ListStudentRow,CheckBox>("checkBox"));
        studentIdCol.setCellValueFactory(new PropertyValueFactory<ListStudentRow,Integer>("id"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<ListStudentRow,String>("name"));
        tableView.getStyleClass().add("center-column");
        tableView.setItems(FXCollections.observableArrayList(resultList));

    }
    public void setValue(int id){
        scheduleId=id;
    }
}
