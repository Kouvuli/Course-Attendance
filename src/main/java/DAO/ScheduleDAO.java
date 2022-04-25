package DAO;

import Entities.Course;
import Entities.Schedule;
import Entities.Student;
import Interfaces.DAOInterface;
import Utils.HibernateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ScheduleDAO implements DAOInterface<Schedule> {
    @Override
    public int addData(Schedule data) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        session.save(data);
        transaction.commit();
        session.close();
        return 0;
    }

    @Override
    public int delData(Schedule data) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        Schedule schedule = session.get(Schedule.class, data.getId());
        if (schedule != null) {

            session.delete(schedule);
        }
        transaction.commit();
        session.close();
        return 0;
    }

    @Override
    public int updateData(Schedule oldData, Schedule newData) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        Schedule schedule=session.get(Schedule.class, oldData.getId());
        schedule.setId(newData.getId());
        schedule.setDateStart(newData.getDateStart());
        schedule.setDateEnd(newData.getDateEnd());
        schedule.setDayOfWeek(newData.getDayOfWeek());
        schedule.setShiftStart(newData.getShiftStart());
        schedule.setShiftEnd(newData.getShiftEnd());
        schedule.setTerm(newData.getTerm());
        schedule.setRoom(newData.getRoom());
        schedule.setYear(newData.getYear());
        session.saveOrUpdate(schedule);
        transaction.commit();
        session.close();
        return 0;
    }
    public void updateStudentSet(int id,Student newStudents){
        Session session=HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        Transaction transaction=session.beginTransaction();
        Schedule schedule=session.get(Schedule.class,id);
        schedule.getStudents().add(newStudents);
        session.saveOrUpdate(schedule);
        transaction.commit();
        session.close();
    }
    @Override
    public ObservableList<Schedule> getAll() {
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query=cb.createQuery(Schedule.class);
        query.from(Schedule.class);
        List<Schedule> list = session.createQuery(query).getResultList();
        return FXCollections.observableArrayList(list);
    }
    public int getTotalPage(int perPage,String term,String year,String dayOfWeek){
        if(perPage==0){
            return -1;
        }
        Session session=HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Schedule> root=countQuery.from(Schedule.class);
        String s1=String.format("%%%s%%",term);
        String s2=String.format("%%%s%%",year);
        String s3=String.format("%%%s%%",dayOfWeek);
        Predicate p1=cb.like(root.get("term").as(String.class),s1);
        Predicate p2=cb.like(root.get("year").as(String.class),s2);
        Predicate p3=cb.like(root.get("dayOfWeek").as(String.class),s3);
        countQuery.where(cb.and(p1,p2,p3));
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
    public ObservableList<Schedule> getPagination(int pageNum,int perPage,String term,String year,String dayOfWeek){
        if(pageNum!=0){
            pageNum=pageNum*perPage;
        }
        Session session=HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();

        CriteriaQuery<Schedule> query=cb.createQuery(Schedule.class);
        Root<Schedule> root = query.from(Schedule.class);
        String s1=String.format("%%%s%%",term);
        String s2=String.format("%%%s%%",year);
        String s3=String.format("%%%s%%",dayOfWeek);
        Predicate p1=cb.like(root.get("term").as(String.class),s1);
        Predicate p2=cb.like(root.get("year").as(String.class),s2);
        Predicate p3=cb.like(root.get("dayOfWeek").as(String.class),s3);
        query.where(cb.and(p1,p2,p3));
        TypedQuery<Schedule> typedQuery = session.createQuery(query.select(root));
        typedQuery.setFirstResult(pageNum);
        typedQuery.setMaxResults(perPage);
        List<Schedule> list=typedQuery.getResultList();
        session.close();
        return FXCollections.observableArrayList(list);
    }

    public ObservableList<String>getAllYear(){
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query=cb.createQuery(Schedule.class);
        Root<Schedule>root=query.from(Schedule.class);
        query.select(root.get("year")).distinct(true);
        List<String> list = session.createQuery(query).getResultList();
        session.close();
        return FXCollections.observableArrayList(list);
    }
    public ObservableList<String> getAllStudentYear(int id){
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query=cb.createQuery(Schedule.class);
        Root<Schedule>root=query.from(Schedule.class);
        query.where(cb.equal(root.get("id").as(Integer.class),id));
        query.select(root.get("year")).distinct(true);
        List<String> list = session.createQuery(query).getResultList();
        session.close();
        return FXCollections.observableArrayList(list);
    }
    public Schedule isAlreadyExist(String courseId,int teacherId,String dayOfWeek,String shiftStart,String shiftEnd,String term,String year){
        Session session=HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query=cb.createQuery(Schedule.class);
        Root<Schedule>root=query.from(Schedule.class);
        String s1=String.format("%%%s%%",courseId);

        String s3=String.format("%%%s%%",dayOfWeek);
        String s4=String.format("%%%s%%",shiftStart);
        String s5=String.format("%%%s%%",shiftEnd);
        String s6=String.format("%%%s%%",term);
        String s7=String.format("%%%s%%",year);
        Predicate p1=cb.like(root.get("course").get("id").as(String.class),s1);
        Predicate p2=cb.equal(root.get("teacher").get("id").as(Integer.class),teacherId);
        Predicate p3=cb.like(root.get("dayOfWeek"),s3);
        Predicate p4=cb.like(root.get("shiftStart"),s4);
        Predicate p5=cb.like(root.get("shiftEnd"),s5);
        Predicate p6=cb.like(root.get("term"),s6);
        Predicate p7=cb.like(root.get("year"),s7);
        query.where(p1,p2,p3,p4,p5,p6,p7);
        Schedule schedule =(Schedule) session.createQuery(query.select(root)).getSingleResult();
        session.close();
        System.out.println("hello world");
        return schedule;

    }
    public Schedule getScheduleById(int id){
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query=cb.createQuery(Schedule.class);
        Root<Schedule>root=query.from(Schedule.class);
        query.where(cb.equal(root.get("id").as(Integer.class),id));

        Schedule schedule =(Schedule) session.createQuery(query).getSingleResult();
        session.close();
        return schedule;
    }
}
