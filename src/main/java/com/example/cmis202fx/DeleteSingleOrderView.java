package com.example.cmis202fx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.example.cmis202fx.Controller.hasSaved;

public class DeleteSingleOrderView {
    public GirlScout currentGS;
    public CookieOrderManager orderManager;
    public Model model;
    @FXML
    public ComboBox<String> cbCookie;
    @FXML
    public ComboBox<String> cbCustomerName;
    @FXML
    public Button cancelOrderBtn;
    @FXML
    public Button deleteOrderBtn;

    public void setScout(GirlScout scout, Model model){
        this.model = model;
        this.currentGS = scout;
        this.orderManager = scout.getCookieOrderManager();
        cbCustomerName.getItems().addAll(orderManager.getCustomerList());
        cbCookie.getItems().addAll(model.getCookieOrderList());
    }

    @FXML
    private void deleteExistingOrder(MouseEvent event){
        String customerName = cbCustomerName.getSelectionModel().getSelectedItem();
        Cookie cookie = getCookie();
        boolean delStatus = orderManager.deleteOrder(customerName, cookie);
        if (delStatus){
            hasSaved = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Deleted");
            alert.setContentText("The order for: " + customerName +
                    "\n has been deleted from " + currentGS.getScoutName() + ".");
            alert.showAndWait();
            Stage stage = (Stage) deleteOrderBtn.getScene().getWindow();
            stage.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Deleting Order");
            alert.setContentText("The order for: " + customerName +
                    "\n could not be deleted. Make sure the customer name and" +
                    " the cookie match to an existing order.");
            alert.showAndWait();
        }
    }

    @FXML
    private void closeOrderView(MouseEvent event){
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
