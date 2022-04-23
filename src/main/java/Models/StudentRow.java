package Models;

import Entities.Student;
import javafx.scene.control.Button;

import java.util.Date;

public class StudentRow extends Student {
    private Button editBtn;
    private Button deleteBtn;
    public StudentRow(){

    }
    public  StudentRow(int id, String name, String cmnd, Date birthday,int phone, String email,boolean isFirst,String username,String password,Button editBtn,Button deleteBtn){
        this.setId(id);
        this.setName(name);
        this.setCmnd(cmnd);
        this.setBirthday(birthday);
        this.setEmail(email);
        this.setPhone(phone);
        this.setFirst(isFirst);
        this.setUsername(username);
        this.setPassword(password);
        this.editBtn=editBtn;
        this.deleteBtn=deleteBtn;
    }
    public void setEditBtn(Button editBtn) {
        this.editBtn = editBtn;
    }

    public void setDeleteBtn(Button deleteBtn) {
        this.deleteBtn = deleteBtn;
    }

    public Button getEditBtn() {
        return editBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }
}
