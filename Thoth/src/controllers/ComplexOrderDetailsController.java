package controllers;

import entity.Indent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import log.ThothLoggerConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

/**
 * Kontroler widoku zamówienia składającego się z kilku zamówień
 */
public class ComplexOrderDetailsController implements Initializable {
    private static final Logger logger = Logger.getLogger(ComplexOrderDetailsController.class);
    @FXML
    private Button backButton;
    @FXML
    private Accordion indentsAccordion;
    private Indent order;
    //ścieżka powrotu
    private String loader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());
    }


    public void setOrder(Indent order) {
        this.order = order;
    }

    public void setLoader(String loader) {
        this.loader = loader;
    }

    /**
     * Metoda inicjalizująca dane potrzebne do wyświetlenia okna zamówienia które składa się z kilu zamówień
     *
     * @throws IOException występuje przy próbie odczytu/zapisu pliku.
     */
    public void initController() throws IOException {
        indentsAccordion.getPanes().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/suborder_details.fxml"));

        Session session = sessionFactory.openSession();

        // pobranie podzamowien
        List<Indent> subOrders = session.createQuery("from Indent where ParentId = :pid").setParameter("pid", order.getIndentId()).list();
        List<TitledPane> subOrdersPanes = new ArrayList<>();

        for (Indent subOrder : subOrders) {
            // dla kazdego podzamowienia tworzony jest TitledPane
            subOrdersPanes.add(createSubOrderPane(subOrder));
        }

        // dodanie TitledPanes do Accordion
        indentsAccordion.getPanes().addAll(subOrdersPanes);
        session.close();
    }


    private TitledPane createSubOrderPane(Indent order) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/suborder_details.fxml"));
        Parent parent = loader.load();
        SimpleOrderDetailsController controller = loader.getController();
        controller.setOrder(order);
        controller.initSubOrderController();

        return new TitledPane("Zamowienie nr: " + order.getIndentId(), parent);
    }


    /**
     * Metoda pozwalająca na powrót do poprzedniego okna.
     *
     * @param event pozwala sprawdzić z jakiego widoku została wywołana metoda
     */
    @FXML
    public void goBack(ActionEvent event) {
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent par = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.loader));
            par = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        stg.setScene(new Scene(Objects.requireNonNull(par)));
    }
}
