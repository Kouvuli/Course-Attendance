package Controllers;

import DAO.ScheduleDAO;
import Entities.Schedule;
import Entities.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class NewStudentScheduleController implements Initializable {
    @FXML
    private VBox vbox;

    private int scheduleId;
    @FXML
    void addCSVFileHandler(ActionEvent event) {
        Stage stage=(Stage) vbox.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Chọn file");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV Chooser", "*.csv");
        fc.getExtensionFilters().add(csvFilter);
        File file = fc.showOpenDialog(stage);
        if(file!=null){
            List<Student> students=readCSVFile(file.getAbsolutePath());
            ScheduleDAO scheduleDAO=new ScheduleDAO();
            Schedule schedule=scheduleDAO.getScheduleById(scheduleId);
            for (Student student : students) {
                scheduleDAO.updateStudentSet(scheduleId,student);

            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setContentText("Thêm thành công");
        alert.showAndWait();
    }

    @FXML
    void addListHandler(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/add-list-student-view.fxml"));
        ListStudentController controller = new ListStudentController();
        controller.setValue(scheduleId);
        loader.setController(controller);
        Stage window=new Stage();
//        window.initModality(Modality.APPLICATION_MODAL);
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene=new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    void addOneHandler(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/add-one-student-view.fxml"));
        OneStudentController controller = new OneStudentController();
        controller.setValue(scheduleId);
        loader.setController(controller);
        Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene=new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public List<Student> readCSVFile(String file_name) {
        List<Student> records=new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file_name))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Student student=null;
                if(values.length==6){
                     student=new Student(Integer.parseInt(values[0]),values[1],values[2],Integer.parseInt(values[3]),new SimpleDateFormat("yyyy-MM-dd").parse(values[4]),values[5]);
                }
                else{

                     student=new Student(Integer.parseInt(values[0]),values[1],values[2],Integer.parseInt(values[3]),new SimpleDateFormat("yyyy-MM-dd").parse(values[4]),values[5],values[6],values[7],false);
                }
                records.add(student);
            }
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }
        return records;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setValue(int id){
        scheduleId=id;
    }
}
