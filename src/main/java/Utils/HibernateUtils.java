package Utils;



import Entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;


public class HibernateUtils {
    private final static SessionFactory FACTORY;
    static {
        Configuration conf=new Configuration();
        Properties props=new Properties();
        props.put(Environment.DIALECT,"org.hibernate.dialect.PostgreSQLDialect");
        props.put(Environment.DRIVER,"org.postgresql.Driver");
        props.put(Environment.URL,"jdbc:postgresql://localhost/StudentManager");
        props.put(Environment.PASS,"1234");
        props.put(Environment.USER,"postgres");
        props.put(Environment.SHOW_SQL,"true");

        conf.setProperties(props);


        conf.addAnnotatedClass(Student.class);
        conf.addAnnotatedClass(Course.class);
        conf.addAnnotatedClass(Teacher.class);
        conf.addAnnotatedClass(Schedule.class);
        conf.addAnnotatedClass(Attendance.class);
        conf.addAnnotatedClass(EnrollCourse.class);

//        conf.addAnnotatedClass(Role.class);
//        conf.addAnnotatedClass(UserRole.class);
//        conf.addAnnotatedClass(RoleSysPrivs.class);
//        conf.addAnnotatedClass(RoleTabPrivs.class);
//        conf.addAnnotatedClass(UserTabPrivs.class);
//        conf.addAnnotatedClass(UserSysPrivs.class);
//        conf.addAnnotatedClass(DBTable.class);
//        conf.addAnnotatedClass(TableColumns.class);
//        conf.addAnnotatedClass(ColumnPermission.class);

        ServiceRegistry registry=new StandardServiceRegistryBuilder()
                .applySettings(conf.getProperties()).build();
        FACTORY=conf.buildSessionFactory(registry);
    }

    public static SessionFactory getFACTORY() {
        return FACTORY;
    }
}
