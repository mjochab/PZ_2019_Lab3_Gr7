package demo;

import org.hibernate.cfg.Configuration;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CreateUser {
    public static void main(String[] args) {

        // sesion factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        // create session
        Session session = factory.getCurrentSession();
        try {
            // create a user object
            System.out.println("Tworzenie nowego uzytkownika");
            User tempUser = new User("admin","admin","admin","admin",1,1);

            // start a transaction
            session.beginTransaction();

            // save the user object
            System.out.println("zapisywanie uzytkownika");
            session.save(tempUser);

            // commit transaction
            session.getTransaction().commit();

            System.out.println("Dziala");
        }
        finally {
            factory.close();
        }

    }
}
