package com.example.cmis202fx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class ViewScoutOrders {
    private GirlScout currentGS;
    private Model model;
    private CookieOrderManager orderManager;
    @FXML
    public Label lblGSName;
    @FXML
    public ListView<String> customerLV;
    @FXML
    public TextArea totalsDisplay;
    @FXML
    public Button viewTPCBtn;
    @FXML
    public Button viewTotalBtn;
    @FXML
    public Button closeBtn;
    @FXML
    public Button orderSumBtn;


    public void setGirlScout(GirlScout scout, Model model){
        this.currentGS = scout;
        this.model = model;
        this.orderManager = currentGS.getCookieOrderManager();
        lblGSName.setText(currentGS.getScoutName() + "'s" + " Orders");
        List<String> customerList = orderManager.getCustomerList();
        customerLV.getItems().addAll(customerList);
        totalsDisplay.setText(orderManager.displayAllOrders());
        totalsDisplay.setEditable(false);
    }

    @FXML
    private void displayCustomerTotal(MouseEvent event){
        String customerName = customerLV.getSelectionModel().getSelectedItem();
        if (customerName == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reminder");
            alert.setContentText("You must select a customer from the list to see their orders.");
            alert.showAndWait();
        }
        else {
            totalsDisplay.clear();
            totalsDisplay.setText(orderManager.displayTotalOrdersPerCustomer(customerName));
        }
    }

    @FXML
    private void displayTotalOrders(MouseEvent event){
        totalsDisplay.clear();
        totalsDisplay.setText(orderManager.displayAllOrders());
    }

    @FXML
    private void displayOrderSummary(MouseEvent event){
        totalsDisplay.clear();
        StringBuilder summary = new StringBuilder("ORDER TOTAL = $" + orderManager.getTotalAmountOfOrders() + "0\n" +
                currentGS.getScoutName() + "'s order will require\n");
        List<String> cookieType = model.getCookieBoxList();
        for (String cookie:
             cookieType) {
            int totalBoxes = orderManager.getNumberBoxesByType(cookie);
            if (totalBoxes == 1){
                String ckName = cookie.replaceAll("_", " ");
                summary.append(totalBoxes).append(" box of ").append(ckName).append("\n");
            }
            else if (totalBoxes > 1) {
                String ckName = cookie.replaceAll("_", " ");
                summary.append(totalBoxes).append(" boxes of ").append(ckName).append("\n");
            }
        }
        totalsDisplay.setText(summary.toString());

    }

    @FXML
    private void closeOrderView(MouseEvent event){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }





}
