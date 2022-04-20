package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ATTENDANCE")
public class Attendance implements Serializable {
    @Id
    @Column(name = "SCHEDULE_ID")
    private int scheduleId;

    @Id
    @Column(name = "STUDENT_ID")
    private int studentId;

    @Id
    private Date day;

    @Column(name = "IS_ATTEND")
    private boolean isAttend;

    @ManyToOne
    @JoinColumn(name = "SCHEDULE_ID", referencedColumnName = "ID")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    private Student student;


    public Attendance(){}

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public void setAttend(boolean attend) {
        isAttend = attend;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getStudentId() {
        return studentId;
    }

    public Date getDay() {
        return day;
    }

    public boolean isAttend() {
        return isAttend;
    }
}
