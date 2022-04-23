package Models;

import Entities.Attendance;
import javafx.scene.control.CheckBox;

public class AttendanceRow {
    private int id;
    private String name;
    private CheckBox[] weeks=new CheckBox[15];
//    private CheckBox week1;
//    private CheckBox week2;
//    private CheckBox week3;
//    private CheckBox week4;
//    private CheckBox week5;
//    private CheckBox week6;
//    private CheckBox week7;
//    private CheckBox week8;
//    private CheckBox week9;
//    private CheckBox week10;
//    private CheckBox week11;
//    private CheckBox week12;
//    private CheckBox week13;
//    private CheckBox week14;
//    private CheckBox week15;


    public AttendanceRow(int id, String name, CheckBox[] weeks) {
        this.id = id;
        this.name = name;
        this.weeks = weeks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeeks(CheckBox[] weeks) {
        this.weeks = weeks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CheckBox[] getWeeks() {
        return weeks;
    }
}
