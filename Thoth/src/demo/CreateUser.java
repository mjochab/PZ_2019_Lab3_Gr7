package demo;


import entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateUser {
    public static void main(String[] args) {

        // sesion factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Shop.class)
                .addAnnotatedClass(Role.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Order_product.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Product_receipt.class)
                .addAnnotatedClass(Receipt.class)
                .addAnnotatedClass(State.class)
                .addAnnotatedClass(State_of_order.class)
                .addAnnotatedClass(State_on_object.class)
                .addAnnotatedClass(User_object.class)
                .buildSessionFactory();

//        // create session
//        Session session = factory.getCurrentSession();
//        try {
//            // create a user object
//            System.out.println("Tworzenie nowego uzytkownika");
//            User tempUser = new User("admin","admin","admin","admin",1,1,1);
//
//            // start a transaction
//            session.beginTransaction();
//
//            // save the user object
//            System.out.println("zapisywanie uzytkownika");
//            session.save(tempUser);
//
//            // commit transaction
//            session.getTransaction().commit();
//
//            System.out.println("Dziala");
//        }
//        finally {
//            factory.close();
//        }

    }
}
