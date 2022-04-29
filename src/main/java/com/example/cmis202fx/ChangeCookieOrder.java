package com.example.cmis202fx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.example.cmis202fx.Controller.hasSaved;

public class ChangeCookieOrder {

    private Model model;
    private CookieOrderManager orderManager;
    private ToggleGroup addRemoveToggle;
    @FXML
    public ComboBox<String> cbCustomerName;
    @FXML
    public ComboBox<String> cbCookie;
    @FXML
    public TextField txtNumberBox;
    @FXML
    public RadioButton rbAdd;
    @FXML
    public RadioButton rbRemove;
    @FXML
    public Button changeOrderBtn;
    @FXML
    public Button cancelOrderBtn;

    public void setChangeOrder(GirlScout scout, Model model){
        this.model = model;
        this.orderManager = scout.getCookieOrderManager();
        cbCustomerName.getItems().addAll(orderManager.getCustomerList());
        cbCookie.getItems().addAll(model.getCookieOrderList());
        addRemoveToggle = new ToggleGroup();
        rbAdd.setToggleGroup(addRemoveToggle);
        rbRemove.setToggleGroup(addRemoveToggle);
    }

    @FXML
    private void changeExistingOrder(MouseEvent event){
        String customerName = cbCustomerName.getSelectionModel().getSelectedItem();
        Cookie cookie = getCookie();
        if (customerName == null || cookie == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Changing Order");
            alert.setContentText("Please Make sure both the name and cookie have values selected.");
            alert.showAndWait();
        }
        int boxNumber = Integer.parseInt(txtNumberBox.getText());
        if (rbRemove.isSelected()){
            String neg = String.valueOf(boxNumber);
            String setNeg = "-" + neg;
            boxNumber = Integer.parseInt(setNeg);
        }
        int changeStatus = orderManager.changeOrder(customerName, cookie, boxNumber);
        checkOrderStatus(customerName, changeStatus);
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

    private void checkOrderStatus(String name, int orderStatus){
        if (rbAdd.isSelected()){
            if (orderStatus == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Changing Order");
                alert.setContentText("Error Changing the order for: " + name + "."
                        + "\n Make sure the name is not empty.");
                alert.showAndWait();
            }
            else if (orderStatus == 51){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Changing Order");
                alert.setContentText("Error Changing the order for: " + name + "."
                        + "\n The amount of cookies added exceeds the limit.");
                alert.showAndWait();
            }
            else if (orderStatus == 52){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Changing Order");
                alert.setContentText("Error Changing the order for: " + name + "."
                        + "\n The order attempting to be changed does not exist.");
                alert.showAndWait();
            }
            else {
                hasSaved = false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Order Changed!");
                alert.setContentText("The order for: " + name +
                        "\n has been changed.");
                alert.showAndWait();
                Stage stage = (Stage) changeOrderBtn.getScene().getWindow();
                stage.close();
            }
        }
        else if (rbRemove.isSelected()){
            if (orderStatus == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Changing Order");
                alert.setContentText("Error Changing the order for: " + name + "."
                        + "\n Make sure the name is not empty or too many boxes were removed.");
                alert.showAndWait();
            }
            else if (orderStatus == 52){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Changing Order");
                alert.setContentText("Error Changing the order for: " + name + "."
                        + "\n The order attempting to be changed does not exist.");
                alert.showAndWait();
            }
            else {
                hasSaved = false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Order Changed!");
                alert.setContentText("The order for: " + name +
                        "\n has been changed.");
                alert.showAndWait();
                Stage stage = (Stage) changeOrderBtn.getScene().getWindow();
                stage.close();
            }
        }
    }
}
