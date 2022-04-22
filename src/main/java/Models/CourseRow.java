package Models;

import Entities.Course;
import javafx.scene.control.Button;

public class CourseRow extends Course {
    private Button editBtn;
    private Button deleteBtn;
    public CourseRow(){}
    public CourseRow(String id,String name,Button editBtn,Button deleteBtn){
        this.setId(id);
        this.setName(name);
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
