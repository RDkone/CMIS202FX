package com.example.cmis202fx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

import static com.example.cmis202fx.Controller.hasSaved;

public class AddCookieOrder {
    private Model model;
    private GirlScout currentGS;
    private CookieOrderManager orderManager;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private ComboBox<String> cbCookie;
    @FXML
    private TextField txtNumberBox;
    @FXML
    private Button addOrderBtn;
    @FXML
    private Button cancelOrderBtn;


    public void setGSOrderManager(GirlScout scout, Model model){
        this.model = model;
        this.currentGS = scout;
        this.orderManager = currentGS.getCookieOrderManager();
        List<String> cookieList = model.getCookieOrderList();
        cbCookie.getItems().addAll(cookieList);
    }


    @FXML
    private void addOrderToScout(MouseEvent event){
        String customerName = txtCustomerName.getText();
        Cookie cookie = getCookie();
        int numOfBoxes = Integer.parseInt(txtNumberBox.getText());
        int getOrder = orderManager.addOrder(customerName, cookie, numOfBoxes);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (getOrder > 0){
            hasSaved = false;
            alert.setTitle("Order Created");
            alert.setContentText(customerName + "'s order for " + getOrder + " boxes of "
            + cookie.getCookieName() + " has been sent to " + currentGS.getScoutName() + ".");
            alert.showAndWait();
            Stage stage = (Stage) addOrderBtn.getScene().getWindow();
            stage.close();
        }
        else {
            alert.setTitle("Error Creating Order");
            alert.setContentText("Error Creating an order for: " + customerName + "."
            + "\n Make sure the name is not empty and the number of boxes is greater than 0");
            alert.showAndWait();
            Stage stage = (Stage) addOrderBtn.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    private void cancelOrder(MouseEvent event){
        Stage stage = (Stage) cancelOrderBtn.getScene().getWindow();
        stage.close();
    }

    private Cookie getCookie(){
        String ck = cbCookie.getSelectionModel().getSelectedItem();
        switch (ck){
            case "Lemon Ups" : return Cookie.LEMONUPS;
            case "Toffee-Tastic" : return Cookie.TOFFEE_TASTIC;
            case "Thin Mints" : return Cookie.THIN_MINTS;
            case "Caramel Chocolate Chip" : return Cookie.CARAMEL_CHOCOLATE_CHIP;
            case "Do-si-dos" : return Cookie.DO_SI_DOS;
            case "Lemonades" : return Cookie.LEMONADES;
            case "Trefoils" : return Cookie.TREFOILS;
            case "Samoas" : return Cookie.SAMOAS;
            case "Tagalongs" : return Cookie.TAGALONGS;
            default: return null;
        }
    }


}
