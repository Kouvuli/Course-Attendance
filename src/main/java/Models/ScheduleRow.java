package Models;

import Entities.Attendance;
import Entities.Course;
import Entities.Schedule;
import Entities.Teacher;
import javafx.scene.control.Button;

import java.util.Date;
import java.util.Set;

public class ScheduleRow extends Schedule {
    private String name;
    private Button attendancesBtn;
    private Button editBtn;
    private Button deleteBtn;
    public ScheduleRow() {
    }
    public ScheduleRow(int id, Date dateStart, Date dateEnd, String dayOfWeek, String shiftStart, String shiftEnd, String room, Teacher teacher, Course course,String name,String term,String year,Button attendancesBtn) {
        this.setId(id);
        this.setDateStart(dateStart);
        this.setDateEnd(dateEnd);
        this.setDayOfWeek(dayOfWeek);
        this.setShiftStart(shiftStart);
        this.setShiftEnd(shiftEnd);
        this.setRoom(room);
        this.setTeacher(teacher);
        this.setCourse(course);
        this.setTerm(term);
        this.setYear(year);
        this.attendancesBtn=attendancesBtn;
        this.name=name;

    }
    public ScheduleRow(int id, Date dateStart, Date dateEnd, String dayOfWeek, String shiftStart, String shiftEnd, String room, Teacher teacher, Course course,String name,String term,String year,Button attendancesBtn,Button editBtn,Button deleteBtn) {
        this.setId(id);
        this.setDateStart(dateStart);
        this.setDateEnd(dateEnd);
        this.setDayOfWeek(dayOfWeek);
        this.setShiftStart(shiftStart);
        this.setShiftEnd(shiftEnd);
        this.setRoom(room);
        this.setTeacher(teacher);
        this.setCourse(course);
        this.setTerm(term);
        this.setYear(year);
        this.name=name;
        this.attendancesBtn=attendancesBtn;
        this.editBtn=editBtn;
        this.deleteBtn=deleteBtn;

    }

    public void setEditBtn(Button editBtn) {
        this.editBtn = editBtn;
    }

    public void setDeleteBtn(Button deleteBtn) {
        this.deleteBtn = deleteBtn;
    }

    public void setAttendancesBtn(Button attendancesBtn) {
        this.attendancesBtn = attendancesBtn;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public Button getAttendancesBtn() {
        return attendancesBtn;
    }

    public Button getEditBtn() {
        return editBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }
}
