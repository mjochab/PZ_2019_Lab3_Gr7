package controllers;

import entity.Shop;
import entity.User;
import entity.UserShop;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.SessionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private String ADMIN = "Admin";
    private String STOREKEEPER = "Magazynier";
    private String SHOP_ASSISTANT = "Sprzedawca";
    private String ANALYST = "Analityk";
    private String LOGISTICIAN = "Logistyk";

    @FXML
    private ComboBox<Shop> comboList;
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label loginErrorLabel;
    @FXML
    private Button loginButton;
    @FXML
    private TextField resetDbButton;
    @FXML
    private Label resetDbLabel;

    public static SessionFactory sessionFactory = null;

    public static SessionContext sessionContext;

    public void switchscene(ActionEvent event) throws IOException {
        System.out.println("URL " + event.getSource().toString());

        if (sessionContext.getCurrentLoggedUser().getRoleId().getPosition().equals(ADMIN)) {
            if (!event.getSource().toString().contains("admin_view")) {
                if (this.comboList.getSelectionModel().getSelectedItem() == null) {
                    System.out.println("Nie wybrano sklepu!");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Napotkano blad");
                    alert.setContentText("Nie wybrano zadnego sklepu/magazynu!");
                    alert.showAndWait();

                    return;
                }
            }
        }

        Parent temporaryLoginParent = null;
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
        if (event.getSource().toString().contains("admin_view") == true) // okno widoku admina - panel administracyjny, wybor sklepu/magazynu
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_admin.fxml"));
        }
        if (event.getSource().toString().contains("admin_choose_employee") == true) // okno widoku admina - pracownicy
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/choose_employee.fxml"));
        }

        Scene temporaryLoginScene = new Scene(temporaryLoginParent);
        // To pobiera informacje o scenie
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(temporaryLoginScene);
        window.show();
    }


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

            UserShop userShopToLoadToSession;

            // Utworzenie sessionContext
            if (ADMIN.equals(user.get(0).getRoleId().getPosition())) {
                sessionContext = new SessionContext(user.get(0));
            } else {
                userShopToLoadToSession = getLoggedUserData(user.get(0));

                if (userShopToLoadToSession != null) {
                    sessionContext = new SessionContext(userShopToLoadToSession);
                } else {
                    System.out.println("Failed to load sessionContext");
                    sessionContext = null;
                }
            }

            Parent temporaryLoginParent = null;

            if (STOREKEEPER.equals(user.get(0).getRoleId().getPosition()) && user.get(0).getState() == 1) //okno magazynu
            {
                temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_warehouse.fxml"));
            }
            if (SHOP_ASSISTANT.equals(user.get(0).getRoleId().getPosition()) && user.get(0).getState() == 1) //okno sklepu
            {
                temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_shop.fxml"));
            }
            if (ANALYST.equals(user.get(0).getRoleId().getPosition()) && user.get(0).getState() == 1) //okno analityka
            {
                temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_analyst.fxml"));
            }
            if (LOGISTICIAN.equals(user.get(0).getRoleId().getPosition()) && user.get(0).getState() == 1) // okno widoku pracownika działy logistycznego
            {
                temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_view_logistic.fxml"));
            }
            if (ADMIN.equals(user.get(0).getRoleId().getPosition()) && user.get(0).getState() == 1) // okno widoku admina
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlfiles/choose_employee.fxml"));
                temporaryLoginParent = loader.load();
                MainWindowController mainController = loader.getController();
                mainController.setComboList();
            }

            if (temporaryLoginParent != null) {
                Scene temporaryLoginScene = new Scene(temporaryLoginParent);
                // To pobiera informacje o scenie
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(temporaryLoginScene);

                window.show();
            }
            loginErrorLabel.setText("Prawdopodobnie konto użytkownika nie jest aktywne");


        } catch (IndexOutOfBoundsException e) {
            loginErrorLabel.setText("Nie ma konta o takim Loginie i Haśle");
        }
    }


    public void resetdb(ActionEvent event) throws IOException {
        System.out.println(resetDbButton.getText());
        if (resetDbButton.getText().contentEquals("DELETE")) {
            SessionFactory factory = new Configuration()
                    .configure("create.cfg.xml").buildSessionFactory();
            resetDbLabel.setText("");
            factory.close();
        } else resetDbLabel.setText("Niepoprawny ciąg znaków");
    }


    private ObservableList<Shop> getShops() {
        ObservableList<Shop> shops = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Shop> shopsList = session.createQuery("from Shop").list();

        shops.addAll(shopsList);

        session.close();
        System.out.println("Zwracam sklepy");
        return shops;

    }


    public void setComboList() {
        this.comboList.getItems().addAll(getShops());
        // jezeli zostanie wybrany inny sklep w comboboxie, zostanie on ustawiony w sessionContext
        this.comboList.valueProperty().addListener(new ChangeListener<Shop>() {
            @Override
            public void changed(ObservableValue<? extends Shop> observable, Shop oldValue, Shop newValue) {
                System.out.println("Poprzednia wartosc: " + oldValue);
                System.out.println("Nowa wartosc: " + newValue);

                sessionContext.setCurrentLoggedShop(newValue);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try{
           sessionFactory = new Configuration().configure("update.cfg.xml").buildSessionFactory();
       }catch (Exception e){
           System.out.println(e.getMessage());
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Niepowodzenie");
           alert.setContentText("NIe udało się nawiązać połączenia z bazą danych!");
           alert.showAndWait();
           System.exit(0);
       }
    }

    public UserShop getLoggedUserData(User userToLogin) {
        Session session = sessionFactory.openSession();

        List<UserShop> userDataList;
        UserShop userData = null;

        try {
            userDataList = session.createQuery("from UserShop us where UserId = :uid")
                    .setParameter("uid", userToLogin.getUserId()).getResultList();

            if (userDataList.size() == 1) {
                userData = userDataList.get(0);
            } else {
                System.out.println("Znaleziono 0 lub > 1 encji w UserShop");
            }
        } catch (Exception e) {
            System.out.println("Blad pobierania z bazy danych");
        }

        session.close();

        return userData;
    }

    public static void newAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
