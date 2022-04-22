package DAO;

import Entities.Attendance;
import Entities.Course;
import Entities.Schedule;
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

public class CourseDAO implements DAOInterface<Course> {
    @Override
    public int addData(Course data) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        session.save(data);
        transaction.commit();
        session.close();
        return 0;
    }

    @Override
    public int delData(Course data) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        ScheduleDAO dao=new ScheduleDAO();
        for(Schedule s:data.getSchedules()){
            dao.delData(s);
        }
        Course course=session.get(Course.class, data.getId());
        if(course!=null){
            session.delete(course);
        }

        transaction.commit();
        session.close();
        return 0;
    }

    @Override
    public int updateData(Course oldData, Course newData) {
        Session session=HibernateUtils.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        Course course=session.get(Course.class, oldData.getId());
        course.setId(newData.getId());
        course.setName(newData.getName());
        session.saveOrUpdate(course);
        transaction.commit();
        session.close();
        return 0;
    }

    @Override
    public ObservableList<Course> getAll() {
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Course.class);
        query.from(Course.class);
        List<Course> list=session.createQuery(query).getResultList();
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
        Root<Course> root=countQuery.from(Course.class);
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
    public ObservableList<Course> getPagination(int pageNum,int perPage){
        if(pageNum!=0){
            pageNum=pageNum*perPage;
        }
        Session session=HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();

        CriteriaQuery<Course> query=cb.createQuery(Course.class);
        Root<Course> root = query.from(Course.class);
        TypedQuery<Course> typedQuery = session.createQuery(query.select(root));
        typedQuery.setFirstResult(pageNum);
        typedQuery.setMaxResults(perPage);
        List<Course> list=typedQuery.getResultList();
        session.close();
        return FXCollections.observableArrayList(list);
    }

    public Course getCourseById(String id){
        Session session=HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query=cb.createQuery(Course.class);
        Root<Course> root=query.from(Course.class);
        String s=String.format("%%%s%%",id);
        query.where(cb.like(root.get("id").as(String.class),s));
        Course course = (Course) session.createQuery(query).getSingleResult();
        return course;
    }
}
