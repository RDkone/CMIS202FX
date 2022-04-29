package com.example.cmis202fx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.example.cmis202fx.Controller.hasSaved;

public class DeleteOrdersView {

    private GirlScout currentGS;
    private CookieOrderManager orderManager;
    @FXML
    public ComboBox<String> cbCustomerName;
    @FXML
    public Button deleteOrderBtn;
    @FXML
    public Button cancelOrderBtn;


    public void setGirlScout(GirlScout scout){
        this.currentGS = scout;
        this.orderManager = scout.getCookieOrderManager();
        cbCustomerName.getItems().addAll(orderManager.getCustomerList());
    }

    @FXML
    private void deleteExistingOrders(MouseEvent event){
        String customerName = cbCustomerName.getSelectionModel().getSelectedItem();
        boolean delStatus = orderManager.deleteOrders(customerName);
        if (delStatus){
            hasSaved = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Deleted");
            alert.setContentText("The orders for: " + customerName +
                    "\n has been deleted from " + currentGS.getScoutName() + ".");
            alert.showAndWait();
            Stage stage = (Stage) deleteOrderBtn.getScene().getWindow();
            stage.close();
        }
    }


    @FXML
    private void closeOrderView(MouseEvent event){
        Stage stage = (Stage) cancelOrderBtn.getScene().getWindow();
        stage.close();
    }
}
