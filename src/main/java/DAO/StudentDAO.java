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
        return 0;
    }

    @Override
    public int updateData(Student oldData, Student newData) {
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

}
