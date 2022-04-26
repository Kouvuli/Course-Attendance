package DAO;

import Entities.Student;
import Entities.Teacher;
import Interfaces.DAOInterface;
import Utils.HibernateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TeacherDAO implements DAOInterface<Teacher> {
    @Override
    public int addData(Teacher data) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        session.save(data);
        transaction.commit();
        session.close();
        return 0;
    }

    @Override
    public int delData(Teacher data) {
        return 0;
    }

    @Override
    public int updateData(Teacher oldData, Teacher newData) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        Teacher teacher=session.get(Teacher.class, oldData.getId());
        teacher.setId(newData.getId());
        teacher.setCmnd(newData.getCmnd());
        teacher.setEmail(newData.getEmail());
        teacher.setName(newData.getName());
        teacher.setPhone(newData.getPhone());
        teacher.setSalary(newData.getSalary());
        teacher.setUsername(newData.getUsername());
        teacher.setPassword(newData.getPassword());
        session.saveOrUpdate(teacher);
        transaction.commit();
        session.close();

        return 0;
    }

    @Override
    public ObservableList<Teacher> getAll() {
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Teacher.class);
        query.from(Teacher.class);
        List<Teacher> list=session.createQuery(query).getResultList();
        session.close();
        return FXCollections.observableArrayList(list);
    }

    public Teacher getTeacherById(int id){
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Teacher.class);
        Root<Teacher> root=query.from(Teacher.class);
        query.where(cb.equal(root.get("id").as(Integer.class),id));
        Teacher teacher= (Teacher) session.createQuery(query).getSingleResult();
        session.close();
        return teacher;
    }
    public Teacher getTeacherByUsername(String username){
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Teacher.class);
        Root<Teacher> root = query.from(Teacher.class);
        String s=String.format("%%%s%%",username);
        query.where(cb.like(root.get("username").as(String.class),s));
        Teacher student=(Teacher) session.createQuery(query.select(root)).getSingleResult();
        session.close();
        return student;
    }
}
