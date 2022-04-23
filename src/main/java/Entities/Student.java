package Entities;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STUDENT")
public class Student implements Serializable {
    @Id
    private int id;

    private String name;

    private String cmnd;

    private int phone;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    private String email;

    private String username;
    private String password;

    @Column(name = "IS_FIRST")
    private boolean isFirst;

    @OneToMany(fetch = FetchType.EAGER,mappedBy="student")
    private Set<Attendance> attendances;


    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "students")
    private Set<Course> courses=new HashSet<>();

    public Student(){}

    public Student(int id,String name,String cmnd,String phone,Date birthday,String email){
        this.id=id;
        this.name=name;
        this.cmnd=cmnd;
        this.phone = Integer.parseInt(phone);
        this.birthday=birthday;
        this.email=email;
        isFirst=true;
        username=String.valueOf(id);
        password= BCrypt.hashpw(username,BCrypt.gensalt(12));
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsFirst() {
        return isFirst;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCmnd() {
        return cmnd;
    }

    public int getPhone() {
        return phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
