package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ATTENDANCE")
public class Attendance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date day;


    @ManyToOne
    @JoinColumn(name = "SCHEDULE_ID", referencedColumnName = "ID")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    private Student student;


    public Attendance(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setStudent(Student student) {
        this.student = student;
    }



    public void setDay(Date day) {
        this.day = day;
    }


    public int getId() {
        return id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Student getStudent() {
        return student;
    }

    public Date getDay() {
        return day;
    }


}
