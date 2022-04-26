package Entities;



import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "TEACHER")
public class Teacher implements Serializable {
    @Id
    private int id;

    private String name;

    private String cmnd;

    private int phone;

    private int salary;

    private String email;

    private String username;
    private String password;

    @OneToMany(mappedBy="teacher")
    private Set<Schedule> schedules;


    public Teacher(){}

    public Teacher(int id, String name, String cmnd, int phone, int salary, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.cmnd = cmnd;
        this.phone = phone;
        this.salary = salary;
        this.email = email;
        this.username = username;
        this.password = BCrypt.hashpw(password,BCrypt.gensalt(12));;
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

    public void setSalary(int salary) {
        this.salary = salary;
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

    public int getSalary() {
        return salary;
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
