package com.example.cmis202fx;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public static boolean hasSaved;
    private Model model;
    private FileChooser fChooser;
    @FXML
    private Label lblTotalTroops;
    @FXML
    private Button addTroopBtn;
    @FXML
    private Button delTroopBtn;
    @FXML
    private Button assignGSTBtn;
    @FXML
    public ListView<Troop> troopLV;
    @FXML
    public ListView<GirlScout> gsLV;
    @FXML
    private Button troopDetailBtn;
    @FXML
    private Button gsOrdersBtn;
    @FXML
    public Label lblTotalGS;
    @FXML
    public Button createGSBtn;
    @FXML
    public Button delGSBtn;
    @FXML
    public Button addCOrderBtn;
    @FXML
    public Button delCOrderBtn;
    @FXML
    public Button changeCOrderBtn;
    @FXML
    public Button saveBtn;
    @FXML
    public Button loadBtn;
    @FXML
    public Button exitBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            hasSaved = true;
            model = new Model();
            fChooser = new FileChooser();
            lblTotalTroops.setText("Total Amount of Troops: " + model.getTroopListSize());
            ObservableList<Troop> oLT = model.getTroopList();
            ObservableList<GirlScout> oLGS = model.getGsList();
            troopLV.setItems(oLT);
            gsLV.setItems(oLGS);
            lblTotalGS.setText("Total Amount of Girl Scouts: " + model.getTotalGS());
            File initPath = new File("C:\\tmp");
            fChooser.setInitialDirectory(initPath);

        } catch (InvalidTroopException e) {
            e.printStackTrace();
        }

        troopLV.getItems().addListener((ListChangeListener<Troop>)
                change ->
                        lblTotalTroops.setText("Total Amount of Troops: " + model.getTroopListSize()));


        gsLV.getItems().addListener((ListChangeListener<GirlScout>)
                change ->
                        lblTotalGS.setText("Total Amount of Girl Scouts: " + model.getTotalGS()));
    }
    // Modal for adding a troop to the database
    @FXML
    private void showCreateTroopInput(MouseEvent event) throws IOException {
        FXMLLoader view = new FXMLLoader(getClass().getResource("AddTroopView.fxml"));
        Parent root = view.load();
        ((AddTroopView) view.getController()).setModel(model);
        Stage stage = new Stage();
        stage.setTitle("Create A New Troop");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
    // Modal for adding a girl scout to the database
    @FXML
    private void showAddAssignGirlScout(MouseEvent event) throws IOException {
        FXMLLoader view = new FXMLLoader(getClass().getResource("AddGScoutView.fxml"));
        Parent root = view.load();
        ((AddGScoutView) view.getController()).setModel(model);
        Stage stage = new Stage();
        stage.setTitle("Add & Assign Girl Scout");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
    // Modal for adding an order to a girl scout
    @FXML
    private void showAddOrderToScout(MouseEvent event) throws IOException {
        GirlScout girlScoutOrder = gsLV.getSelectionModel().getSelectedItem();
        if (girlScoutOrder == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reminder");
            alert.setContentText("You must select a girl scout from the list below to add an order.");
            alert.showAndWait();
        } else {
            FXMLLoader view = new FXMLLoader(getClass().getResource("AddCookieOrder.fxml"));
            Parent root = view.load();
            ((AddCookieOrder) view.getController()).setGSOrderManager(girlScoutOrder, model);
            Stage stage = new Stage();
            stage.setTitle("Cookie Order");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }

    }
    // Modal for changing an order from a girl scout
    @FXML
    private void showChangeOrder(MouseEvent event) throws IOException {
        GirlScout girlScoutOrder = gsLV.getSelectionModel().getSelectedItem();
        if (girlScoutOrder == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reminder");
            alert.setContentText("You must select a girl scout from the list below to add an order.");
            alert.showAndWait();
        } else if (girlScoutOrder.getCookieOrderManager().getCustomerList().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reminder");
            alert.setContentText("There are no orders to change for this girl scout.");
            alert.showAndWait();
        } else {
            FXMLLoader view = new FXMLLoader(getClass().getResource("ChangeCookieOrder.fxml"));
            Parent root = view.load();
            ((ChangeCookieOrder) view.getController()).setChangeOrder(girlScoutOrder, model);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }


    }
    // Selecting a troop from the ListView and removing it from the database
    @FXML
    private void removeSelectedTroop(MouseEvent event) {
        Troop troop = troopLV.getSelectionModel().getSelectedItem();
        if (troop == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reminder");
            alert.setContentText("You must select a troop from the list below to delete it.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you wish to delete " + troop.getTroopName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                List<GirlScout> gsL = troop.getTroopMembers();
                for (GirlScout gs : gsL) {
                    model.deletedGS(gs);
                }
                model.removeTroop(troop);
                Alert removedAlert = new Alert(Alert.AlertType.INFORMATION);
                removedAlert.setTitle("Troop deleted");
                removedAlert.setContentText(troop.getTroopName() + " has been deleted.");
                removedAlert.showAndWait();
            }
        }
    }
    // Selecting a girl scout from the ListView and removing it from the database
    @FXML
    private void removedSelectedGirlScout(MouseEvent event) {
        GirlScout girlScout = gsLV.getSelectionModel().getSelectedItem();
        if (girlScout == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reminder");
            alert.setContentText("You must select a girl scout from the list below to delete it.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you wish to delete " + girlScout.getScoutName() +
                    " From " + girlScout.getTroopName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Troop troop = model.getScoutTroops().getTroopFromTroopName(girlScout.getTroopName());
                troop.removeMember(girlScout.getScoutName());
                model.deletedGS(girlScout);
                Alert removedAlert = new Alert(Alert.AlertType.INFORMATION);
                removedAlert.setTitle("Scout Removed");
                removedAlert.setContentText(girlScout.getScoutName() + " has been removed.");
                removedAlert.showAndWait();
            }
        }
    }
    // Modal -- Removing an order from a girl scout
    @FXML
    private void removeCookieOrder(MouseEvent event) throws IOException {
        GirlScout girlScout = gsLV.getSelectionModel().getSelectedItem();
        if (girlScout == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reminder");
            alert.setContentText("You must select a girl scout from the list below to delete an order.");
            alert.showAndWait();
        } else if (girlScout.getCookieOrderManager().getCustomerList().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reminder");
            alert.setContentText("There are no orders to delete for this girl scout.");
            alert.showAndWait();
        } else {
            ButtonType single = new ButtonType("Single", ButtonBar.ButtonData.OK_DONE);
            ButtonType multiple = new ButtonType("Multiple", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Would you like to delete a single" +
                    " or multiple orders from this girl scout?", single, multiple);
            alert.setTitle("Order Deletion");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == single) {
                FXMLLoader view = new FXMLLoader(getClass().getResource("DeleteSingleOrderView.fxml"));
                Parent root = view.load();
                ((DeleteSingleOrderView) view.getController()).setScout(girlScout, model);
                Stage stage = new Stage();
                stage.setTitle("Delete an Order");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
            } else if (result.isPresent() && result.get() == multiple) {
                FXMLLoader view = new FXMLLoader(getClass().getResource("DeleteOrdersView.fxml"));
                Parent root = view.load();
                ((DeleteOrdersView) view.getController()).setGirlScout(girlScout);
                Stage stage = new Stage();
                stage.setTitle("Delete Orders");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
            }

        }
    }
    // Modal -- Shows more info about a selected troop
    @FXML
    private void expandTroopInfo(MouseEvent event) throws IOException {
        FXMLLoader view = new FXMLLoader(getClass().getResource("TroopInfoView.fxml"));
        Parent root = view.load();

        if (troopLV.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        ((TroopView) view.getController()).setTroop(troopLV.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.setTitle("Troop Detail View");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
    // Modal -- Shows the current pending orders of a girl scout.
    @FXML
    private void expandGSOrders(MouseEvent event) throws IOException {
        GirlScout girlScout = gsLV.getSelectionModel().getSelectedItem();
        if (girlScout == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reminder");
            alert.setContentText("You must select a girl scout from the list below to view her orders.");
            alert.showAndWait();
        } else {
            List<String> pendingOrders = girlScout.getCookieOrderManager().getCustomerList();
            if (!(pendingOrders.size() > 0)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setContentText(girlScout.getScoutName() + " does not currently have any pending orders.");
                alert.showAndWait();
            } else {
                FXMLLoader view = new FXMLLoader(getClass().getResource("ViewScoutOrders.fxml"));
                Parent root = view.load();
                ((ViewScoutOrders) view.getController()).setGirlScout(girlScout, model);
                Stage stage = new Stage();
                stage.setTitle(girlScout.getScoutName());
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
            }
        }

    }
    // Save any changes made to the project to a file
    @FXML
    private void saveProject(MouseEvent event) {
        save(1);
    }
    // Load previous projects that were saved to a file
    @FXML
    private void loadSavedProject(MouseEvent event) {
        Window win = loadBtn.getScene().getWindow();
        fChooser.setTitle("Load Project File");
        fChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("data file", "*.dat"));
        File loadFile = fChooser.showOpenDialog(win);
        if (loadFile != null){
            fChooser.setInitialDirectory(loadFile.getParentFile());
            boolean loadStatus = model.loadModelFromFile(loadFile);
            if (loadStatus){
                hasSaved = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Loaded");
                alert.setContentText("File has been loaded successfully.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Loading File");
                alert.setContentText("A Problem occurred attempting to read this file to load data.");
                alert.showAndWait();
            }
        }

    }
    // Exiting the program
    @FXML
    private void systemExit(MouseEvent event) {
        if (!hasSaved){
            ButtonType exit = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
            ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING, "You have not saved any changes" +
                    " you have made to the program, do you still wish to exit?" + "\n" +
                    "Would you like to exit the program?", exit, save, cancel);
            alert.setTitle("Exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == exit){
                System.exit(3);
            }
            if (result.isPresent() && result.get() == save){
                save(2);
            }
        }
        else {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want" + "\n" +
                    "to exit the program?", yes, no);
            alert.setTitle("Exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yes){
                System.exit(4);
            }
        }
    }

    public boolean save(int saveMode){
        boolean isExiting = true;
        if (saveMode == 1){
            Window win = saveBtn.getScene().getWindow();
            fChooser.setTitle("Save Project");
            fChooser.setInitialFileName("GSManager.dat");
            fChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("data file", "*.dat"));
            File saveFile = fChooser.showSaveDialog(win);
            if (saveFile != null){
                fChooser.setInitialDirectory(saveFile.getParentFile());
                boolean saveStatus = model.saveModelToFile(saveFile);
                if (saveStatus){
                    hasSaved = true;
                    saveExit();
                }
            }
        }
        else if (saveMode == 2){
            Window win = saveBtn.getScene().getWindow();
            fChooser.setTitle("Save Project");
            fChooser.setInitialFileName("GSManager.dat");
            fChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("data file", "*.dat"));
            File saveFile = fChooser.showSaveDialog(win);
            if (saveFile != null){
                fChooser.setInitialDirectory(saveFile.getParentFile());
                boolean saveStatus = model.saveModelToFile(saveFile);
                if (saveStatus){
                    hasSaved = true;
                    isExiting = saveExitFromExit();
                }
            }
        }
        return isExiting;
    }
    public void saveExit(){
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save Successful!" + "\n" +
                "Would you like to exit the program?", yes, no);
        alert.setTitle("Save/Exit");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yes){
            System.exit(2);
        }
    }
    public boolean saveExitFromExit(){
        boolean exitConfirmed = true;
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save Successful!" + "\n" +
                "Would you still like to exit the program?", yes, no);
        alert.setTitle("Save/Exit");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yes){
        }
        else if (result.isPresent()  && result.get() == no){
            exitConfirmed = false;
        }
        return exitConfirmed;
    }
    public boolean handleSystemExit() {
        boolean exitStatus = true;
        if (!hasSaved){
            ButtonType exit = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
            ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING, "You have not saved any changes" +
                    " you have made to the program, do you still wish to exit?" + "\n" +
                    "Would you like to exit the program?", exit, save, cancel);
            alert.setTitle("Exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == exit){
            }
            if (result.isPresent() && result.get() == save){
                exitStatus = save(2);
            }
            if (result.isPresent() && result.get() == cancel){
                exitStatus = false;
            }

        }
        else {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want" + "\n" +
                    "to exit the program?", yes, no);
            alert.setTitle("Exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yes){
                exitStatus = true;
            }
            else if (result.isPresent() && result.get() == no){
                exitStatus = false;
            }
        }
        return exitStatus;
    }


}
