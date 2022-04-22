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
    public ScheduleRow() {
    }
    public ScheduleRow(int id, Date dateStart, Date dateEnd, String dayOfWeek, String shiftStart, String shiftEnd, String room, Teacher teacher, Course course,String name,String term,String year) {
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

    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
