package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COURSE")
public class Course implements Serializable {
    @Id
    private String id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "course")
    private Set<Schedule> schedules=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.ALL })
    @JoinTable(
            name = "ENROLL_COURSE",
            joinColumns = { @JoinColumn(name = "COURSE_ID",referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "STUDENT_ID",referencedColumnName = "ID") }
    )
    Set<Student> students = new HashSet<>();

    public Course(){}
    public Course(String id,String name){
        this.id=id;
        this.name=name;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
