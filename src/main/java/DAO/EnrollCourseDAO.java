package DAO;

import Entities.EnrollCourse;
import Interfaces.DAOInterface;
import Utils.HibernateUtils;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;

public class EnrollCourseDAO implements DAOInterface<EnrollCourse> {
    @Override
    public int addData(EnrollCourse data) {
        return 0;
    }

    @Override
    public int delData(EnrollCourse data) {
        Session session= HibernateUtils.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        EntityManager em=session.getEntityManagerFactory().createEntityManager();
        em.persist(data);
        session.delete(data);
        transaction.commit();
        session.close();
        return 0;
    }

    @Override
    public int updateData(EnrollCourse oldData, EnrollCourse newData) {
        return 0;
    }

    @Override
    public ObservableList<EnrollCourse> getAll() {
        return null;
    }
}
