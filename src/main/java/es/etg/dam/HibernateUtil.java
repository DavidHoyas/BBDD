package es.etg.dam;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration config = new Configuration();

            config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3307/institutodb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
            config.setProperty("hibernate.connection.username", "usuario");
            config.setProperty("hibernate.connection.password", "contraseña");
            config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            config.setProperty("hibernate.show_sql", "true");
            config.setProperty("hibernate.format_sql", "true");
            config.setProperty("hibernate.hbm2ddl.auto", "update");

            config.addAnnotatedClass(AlumnoHibernate.class);
            config.addAnnotatedClass(AsignaturaHibernate.class);

            return config.buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Error SessionFactory: " + ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
