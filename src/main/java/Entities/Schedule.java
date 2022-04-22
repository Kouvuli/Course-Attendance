package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "SCHEDULE")
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DATE_START")
    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @Column(name = "DATE_END")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;

    @Column(name = "DAY_OF_WEEK")
    private String dayOfWeek;

    @Column(name = "SHIFT_START")
    private String shiftStart;

    @Column(name = "SHIFT_END")
    private String shiftEnd;

    private String room;
    private String term;
    private String year;

    @OneToMany(mappedBy="schedule")
    private Set<Attendance> attendances;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID", referencedColumnName = "ID")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID", referencedColumnName = "ID")
    private Course course;

    public Schedule(){};

    public void setTerm(String term) {
        this.term = term;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setShiftStart(String shiftStart) {
        this.shiftStart = shiftStart;
    }

    public void setShiftEnd(String shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public String getTerm() {
        return term;
    }

    public String getYear() {
        return year;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getShiftStart() {
        return shiftStart;
    }

    public String getShiftEnd() {
        return shiftEnd;
    }

    public String getRoom() {
        return room;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Course getCourse() {
        return course;
    }
}
