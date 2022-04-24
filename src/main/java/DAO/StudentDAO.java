package DAO;

import Entities.*;
import Interfaces.DAOInterface;
import Utils.HibernateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class StudentDAO implements DAOInterface<Student> {
    @Override
    public int addData(Student data) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        session.save(data);
        transaction.commit();
        session.close();
        return 0;
    }

    @Override
    public int delData(Student data) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        AttendanceDAO attendanceDAO=new AttendanceDAO();
        for(Attendance attendance:data.getAttendances()){
            attendanceDAO.delData(attendance);
        }

        Student student = session.get(Student.class, data.getId());
        if (student != null) {

            session.delete(student);
        }
        transaction.commit();
        session.close();
        return 0;
    }

    @Override
    public int updateData(Student oldData, Student newData) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        Student student=session.get(Student.class, oldData.getId());
        student.setId(newData.getId());
        student.setBirthday(newData.getBirthday());
        student.setCmnd(newData.getCmnd());
        student.setEmail(newData.getEmail());
        student.setName(newData.getName());
        student.setPhone(newData.getPhone());
        student.setUsername(newData.getUsername());
        student.setPassword(newData.getPassword());
        student.setFirst(newData.getIsFirst());
        session.saveOrUpdate(student);
        transaction.commit();
        session.close();

        return 0;
    }

    @Override
    public ObservableList<Student> getAll() {
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Student.class);
        query.from(Student.class);
        List<Student> list=session.createQuery(query).getResultList();
        session.close();
        return FXCollections.observableArrayList(list);
    }
    public int getTotalPage(int perPage){
        if(perPage==0){
            return -1;
        }
        Session session=HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Student> root=countQuery.from(Student.class);
        countQuery.select(cb.count(root));
        Long count=session.createQuery(countQuery).getSingleResult();
        session.close();
        if(count<=perPage){
            return 1;
        }
        if(count%perPage!=0){

            return (int)(count/perPage)+1;
        }
        else{
            return (int)(count/perPage);
        }
    }
    public ObservableList<Student> getPagination(int pageNum,int perPage){
        if(pageNum!=0){
            pageNum=pageNum*perPage;
        }
        Session session=HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();

        CriteriaQuery<Student> query=cb.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);
        TypedQuery<Student> typedQuery = session.createQuery(query.select(root));
        typedQuery.setFirstResult(pageNum);
        typedQuery.setMaxResults(perPage);
        List<Student> list=typedQuery.getResultList();
        session.close();
        return FXCollections.observableArrayList(list);
    }
    public Student getStudentByUsername(String username){
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);
        String s=String.format("%%%s%%",username);
        query.where(cb.like(root.get("username").as(String.class),s));
        Student student=(Student) session.createQuery(query.select(root)).getSingleResult();
        session.close();
        return student;
    }
    public Student getStudentById(int id){
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);
        query.where(cb.equal(root.get("id").as(Integer.class),id));
        Student student=(Student) session.createQuery(query.select(root)).getSingleResult();
        session.close();
        return student;
    }
}
