package DAO;

import Entities.Schedule;
import Interfaces.DAOInterface;
import Utils.HibernateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ScheduleDAO implements DAOInterface {
    @Override
    public int addData(Object data) {
        return 0;
    }

    @Override
    public int delData(Object data) {
        return 0;
    }

    @Override
    public int updateData(Object newData) {
        return 0;
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
        if(count<=perPage){
            return 1;
        }
        return (int)(count/perPage)+1;
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

        return FXCollections.observableArrayList(list);
    }

    public ObservableList<String>getAllYear(){
        Session session= HibernateUtils.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query=cb.createQuery(Schedule.class);
        Root<Schedule>root=query.from(Schedule.class);
        query.select(root.get("year")).distinct(true);
        List<String> list = session.createQuery(query).getResultList();
        return FXCollections.observableArrayList(list);
    }

}
