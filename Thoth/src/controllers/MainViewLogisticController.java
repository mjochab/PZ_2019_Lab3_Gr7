package controllers;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import models.Order;
import models.Product;

public class MainViewLogisticController implements Initializable {

    @FXML private LogisticOrdersController ordersController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Hello from MainController");
    }
}
