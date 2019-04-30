package demo;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateUser {
    public static void main(String[] args) {

        // sesion factory
        SessionFactory factory = new Configuration()
                .configure("create.cfg.xml")
                //tabele "Wolne"


                //tabele łączone

//                .addAnnotatedClass(State_on_shop.class)
//                .addAnnotatedClass(User.class)
//                .addAnnotatedClass(UserShop.class)

//                .addAnnotatedClass(Receipt.class)


//                .addAnnotatedClass(Product_receipt.class)
//                .addAnnotatedClass(Indent.class)
//                .addAnnotatedClass(State_of_indent.class)
//                .addAnnotatedClass(Indent_product.class)
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
