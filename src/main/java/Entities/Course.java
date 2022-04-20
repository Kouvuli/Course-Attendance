package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COURSE")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    @OneToMany(mappedBy = "course")
    private Set<Schedule> schedules;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "ENROLL_COURSE",
            joinColumns = { @JoinColumn(name = "COURSE_ID",referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "STUDENT_ID",referencedColumnName = "ID") }
    )
    Set<Student> students = new HashSet<>();
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
