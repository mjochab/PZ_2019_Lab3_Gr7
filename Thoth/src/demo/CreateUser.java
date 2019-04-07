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
                //tabele "Wolne"
                .addAnnotatedClass(State.class)
                .addAnnotatedClass(Role.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Shop.class)
                .addAnnotatedClass(Customer.class)


                //tabele łączone

//                .addAnnotatedClass(State_on_object.class)
//                .addAnnotatedClass(User.class)
//                .addAnnotatedClass(User_object.class)

//                .addAnnotatedClass(Receipt.class)


//                .addAnnotatedClass(Product_receipt.class)
//                .addAnnotatedClass(Order.class)
//                .addAnnotatedClass(State_of_order.class)
//                .addAnnotatedClass(Order_product.class)
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
