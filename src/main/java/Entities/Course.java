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



    public Course(){}
    public Course(String id,String name){
        this.id=id;
        this.name=name;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
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


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
