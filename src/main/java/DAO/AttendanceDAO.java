package DAO;

import Entities.Attendance;
import Entities.Schedule;
import Entities.Student;
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

public class AttendanceDAO implements DAOInterface<Attendance> {
    @Override
    public int addData(Attendance data) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        session.save(data);
        transaction.commit();
        session.close();
        return 0;
    }

    @Override
    public int delData(Attendance data) {

        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        Attendance attendance = session.get(Attendance.class, data.getId());
        if (attendance != null) {
            session.delete(attendance);
        }
        transaction.commit();
        session.close();

        return 0;
    }

    @Override
    public int updateData(Attendance oldData, Attendance newData) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        session.get(Attendance.class,oldData.getStudent().getId());
        transaction.commit();
        session.close();
        return 0;
    }

    @Override
    public ObservableList<Attendance> getAll() {
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Attendance.class);
        query.from(Attendance.class);
        List<Attendance> list=session.createQuery(query).getResultList();
        return FXCollections.observableArrayList(list);
    }
    public Attendance getAttendanceById(int id){
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query=cb.createQuery(Attendance.class);
        Root<Attendance> root=query.from(Attendance.class);
        query.where(cb.equal(root.get("id").as(Integer.class),id));

        Attendance attendance =(Attendance) session.createQuery(query).getSingleResult();
        session.close();
        return attendance;
    }
//    public Attendance getAttendance(String studentId,String courseId,String ,){
//
//    }

}
