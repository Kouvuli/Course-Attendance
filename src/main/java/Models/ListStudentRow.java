package Models;

import Entities.Student;
import javafx.scene.control.CheckBox;

public class ListStudentRow {
    private CheckBox checkBox;
    private int id;
    private String name;
    private Student student;
    public ListStudentRow(){}
    public ListStudentRow(CheckBox checkBox, int id, String name,Student student) {
        this.checkBox = checkBox;
        this.id = id;
        this.name = name;
        this.student=student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student getStudent() {
        return student;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
