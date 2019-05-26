package controllers;

import entity.Shop;
import entity.User;
import entity.UserShop;
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
import log.ThothLoggerConfigurator;
import models.SessionContext;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Kontroller głównego okna aplikacji
 */
public class MainWindowController implements Initializable {
    private static final Logger logger = Logger.getLogger(MainWindowController.class);
    private final String ADMIN = "Admin";
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

    /**
     * Metoda służy do przełączania pomiędzy widokami.
     * W zależności od zdarzenia wczytuje odpowiednie widoki.
     *
     * @param event pozwala zlokalizować z jakiego okna wywołano metodę
     * @throws IOException występuje przy odczycie/zapisie pliku
     */
    public void switchscene(ActionEvent event) throws IOException {
        logger.warn("URL " + event.getSource().toString());

        if (sessionContext.getCurrentLoggedUser().getRoleId().getPosition().equals(ADMIN)) {
            if (!event.getSource().toString().contains("admin_view") &&             // jezeli nie wybrano panelu admina lub
                    !event.getSource().toString().contains("analyst")) {                // panelu analityka
                if (this.comboList.getSelectionModel().getSelectedItem() == null) { // i nie wybrano zadnego sklepu
                    logger.warn("Nie wybrano sklepu!");                      // wyswietl ostrzezenie

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Napotkano blad");
                    alert.setContentText("Nie wybrano zadnego sklepu/magazynu!");
                    alert.showAndWait();

                    return;
                }
            }
        }

        Parent temporaryLoginParent = null;
        if (event.getSource().toString().contains("back")) //tu bedzie id = "back", przycisk powrotu
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/MainWindow.fxml"));
        }
        if (event.getSource().toString().contains("employee_warehouse")) //okno magazynu
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_warehouse.fxml"));
        }
        if (event.getSource().toString().contains("employee_shop")) //okno sklepu
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_shop.fxml"));
        }
        if (event.getSource().toString().contains("analyst")) //okno analityka
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_analyst.fxml"));
        }
        if (event.getSource().toString().contains("employee_logistic")) // okno widoku pracownika działy logistycznego
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_view_logistic.fxml"));
        }
        if (event.getSource().toString().contains("admin_view")) // okno widoku admina - panel administracyjny, wybor sklepu/magazynu
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_admin.fxml"));
        }
        if (event.getSource().toString().contains("admin_choose_employee")) // okno widoku admina - pracownicy
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/choose_employee.fxml"));
        }

        Scene temporaryLoginScene = new Scene(Objects.requireNonNull(temporaryLoginParent));
        // To pobiera informacje o scenie
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(temporaryLoginScene);
        window.show();
    }


    /**
     * Metoda pobiera z bazy rekord zawierający login i hasło wpisane w polach klasy: loginTextField, passwordTextField.
     *
     * @param event
     * @throws IOException
     */
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
            logger.warn(user.get(0).getRoleId().getPosition().getClass());

            UserShop userShopToLoadToSession;

            // Utworzenie sessionContext
            /*
                Jezeli zalogowany uzytkownik to ADMINISTRATOR
                    utworz obiekt SessionContext tylko z obiektem User
                w przeciwnym wypadku
                    pobierz dane sklepu
                    jezeli dane sa rozne od null
                        utworz obiekt SessionContext z obiektu User_shop
                    w przeciwnym wypadku SessionContext = null
             */
            if (ADMIN.equals(user.get(0).getRoleId().getPosition())) {
                sessionContext = new SessionContext(user.get(0));
            } else {
                userShopToLoadToSession = getLoggedUserData(user.get(0));

                if (userShopToLoadToSession != null) {
                    sessionContext = new SessionContext(userShopToLoadToSession);
                } else {
                    logger.warn("Failed to load sessionContext");
                    sessionContext = null;
                }
            }

            Parent temporaryLoginParent = null;

            String STOREKEEPER = "Magazynier";
            if (STOREKEEPER.equals(user.get(0).getRoleId().getPosition()) && user.get(0).getState() == 1) //okno magazynu
            {
                temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_warehouse.fxml"));
            }
            String SHOP_ASSISTANT = "Sprzedawca";
            if (SHOP_ASSISTANT.equals(user.get(0).getRoleId().getPosition()) && user.get(0).getState() == 1) //okno sklepu
            {
                temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_shop.fxml"));
            }
            String ANALYST = "Analityk";
            if (ANALYST.equals(user.get(0).getRoleId().getPosition()) && user.get(0).getState() == 1) //okno analityka
            {
                temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_analyst.fxml"));
            }
            String LOGISTICIAN = "Logistyk";
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


    /**
     * Metoda resetuje bazę danych i wczytuje pliki z pliku import_1.sql
     */
    public void resetdb() {
        logger.warn(resetDbButton.getText());
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
        logger.warn("Zwracam sklepy");
        return shops;

    }


    public void setComboList() {
        this.comboList.getItems().addAll(getShops());
        // jezeli zostanie wybrany inny sklep w comboboxie, zostanie on ustawiony w sessionContext
        this.comboList.valueProperty().addListener((observable, oldValue, newValue) -> {
            logger.warn("Poprzednia wartosc: " + oldValue);
            logger.warn("Nowa wartosc: " + newValue);

            sessionContext.setCurrentLoggedShop(newValue);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());
        try {
            sessionFactory = new Configuration().configure("update.cfg.xml").buildSessionFactory();
        } catch (Exception e) {
            logger.warn(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niepowodzenie");
            alert.setContentText("NIe udało się nawiązać połączenia z bazą danych!");
            alert.showAndWait();
            System.exit(0);
        }
    }

    private UserShop getLoggedUserData(User userToLogin) {
        Session session = sessionFactory.openSession();

        List<UserShop> userDataList;
        UserShop userData = null;

        try {
            userDataList = session.createQuery("from UserShop us where UserId = :uid")
                    .setParameter("uid", userToLogin.getUserId()).getResultList();

            if (userDataList.size() == 1) {
                userData = userDataList.get(0);
            } else {
                logger.warn("Znaleziono 0 lub > 1 encji w UserShop");
            }
        } catch (Exception e) {
            logger.warn("Blad pobierania z bazy danych");
        }

        session.close();

        return userData;
    }
}
