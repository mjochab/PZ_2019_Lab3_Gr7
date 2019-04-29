package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.List;
import java.util.ResourceBundle;

import entity.Shop;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import org.hibernate.query.Query;

public class MainWindowController implements Initializable {

    private String ADMIN = "Admin";
    private String STOREKEEPER = "Magazynier";
    private String SHOP_ASSISTANT = "Sprzedawca";
    private String ANALYST = "Analityk";
    private String LOGISTICIAN = "Logistyk";

    @FXML
    private ComboBox<Shop> comboList;

    private ObservableList<Shop> getShops()
    {
        ObservableList<Shop> shops = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Shop> shopsList = session.createQuery("from Shop").list();

        shops.addAll(shopsList);

        session.close();
        System.out.println("Zwracam sklepy!");
        return shops;
    }

    // dodaje sklepy do ComboListy
    public void setComboList()
    {
        //this.comboList.getItems().addAll(getShops());
    }

    public void switchscene(ActionEvent event) throws IOException {
        System.out.println(event.getSource().toString());

        Parent temporaryLoginParent = null;
        if (event.getSource().toString().contains("login_btn") == true) //logowanie
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/TemporaryLogIn.fxml"));
        }
        if (event.getSource().toString().contains("back") == true) //tu bedzie id = "back", przycisk powrotu
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/MainWindow.fxml"));
        }
        if (event.getSource().toString().contains("employee_warehouse") == true) //okno magazynu
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_warehouse.fxml"));
        }
        if (event.getSource().toString().contains("employee_shop") == true) //okno sklepu
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_shop.fxml"));
        }
        if (event.getSource().toString().contains("analyst") == true) //okno analityka
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_analyst.fxml"));
        }
        if (event.getSource().toString().contains("employee_logistic") == true) // okno widoku pracownika działy logistycznego
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_view_logistic.fxml"));
        }
        if (event.getSource().toString().contains("admin_view") == true) // okno widoku admina
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_admin.fxml"));
        }
        if (event.getSource().toString().contains("admin_choose_employee") == true) // okno widoku admina
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/choose_employee.fxml"));
        }


        Scene temporaryLoginScene = new Scene(temporaryLoginParent);

        // To pobiera informacje o scenie
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(temporaryLoginScene);
        window.show();
    }

    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label loginErrorLabel;
    @FXML
    private Button loginButton;

    public static final SessionFactory sessionFactory = new Configuration().configure("update.cfg.xml").buildSessionFactory();


    public void login(ActionEvent event) throws IOException {
        loginErrorLabel.setText("");
        List<User> user;

        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User U where login = :login and password = :password");
        query.setParameter("login", loginTextField.getText());
        query.setParameter("password", passwordTextField.getText());
        user = query.list();
        session.close();
        try {
            System.out.println(user.get(0).getRoleId().getPosition().getClass());

            Parent temporaryLoginParent = null;

            if (STOREKEEPER.equals(user.get(0).getRoleId().getPosition())) //okno magazynu
            {
                temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_warehouse.fxml"));
            }
            if (SHOP_ASSISTANT.equals(user.get(0).getRoleId().getPosition())) //okno sklepu
            {
                temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_shop.fxml"));
            }
            if (ANALYST.equals(user.get(0).getRoleId().getPosition())) //okno analityka
            {
                temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_analyst.fxml"));
            }
            if (LOGISTICIAN.equals(user.get(0).getRoleId().getPosition())) // okno widoku pracownika działy logistycznego
            {
                temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_view_logistic.fxml"));
            }
            if (ADMIN.equals(user.get(0).getRoleId().getPosition())) // okno widoku admina
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlfiles/choose_employee.fxml"));
                //temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/choose_employee.fxml"));
                temporaryLoginParent = loader.load();
                MainWindowController mainController = loader.getController();
                mainController.setComboList();
            }


            Scene temporaryLoginScene = new Scene(temporaryLoginParent);

            // To pobiera informacje o scenie
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(temporaryLoginScene);
            window.show();

        } catch (IndexOutOfBoundsException e) {
            loginErrorLabel.setText("Nie ma konta o takim Loginie i Haśle");
        }

    }

    @FXML
    private TextField resetDbButton;
    @FXML
    private Label resetDbLabel;

    public void resetdb(ActionEvent event) throws IOException {
        System.out.println(resetDbButton.getText());
        if (resetDbButton.getText().contentEquals("DELETE")) {
            SessionFactory factory = new Configuration()
                    .configure("create.cfg.xml").buildSessionFactory();
            resetDbLabel.setText("");
            factory.close();
        } else resetDbLabel.setText("Niepoprawny ciąg znaków");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
