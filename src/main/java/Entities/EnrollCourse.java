package Entities;

import Interfaces.DAOInterface;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ENROLL_COURSE")
public class EnrollCourse implements Serializable {
    @Id
    @Column(name = "SCHEDULE_ID")
    private int scheduleId;

    @Id
    @Column(name = "STUDENT_ID")
    private int studentId;

    @ManyToOne
    @JoinColumn(name = "SCHEDULE_ID",referencedColumnName = "ID")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID",referencedColumnName = "ID")
    private Student student;
    public EnrollCourse() {
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getStudentId() {
        return studentId;
    }
}
