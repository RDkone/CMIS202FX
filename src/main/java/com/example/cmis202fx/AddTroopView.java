package com.example.cmis202fx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static com.example.cmis202fx.Controller.hasSaved;

public class AddTroopView {
    private Model model;
    @FXML
    public Label lblID;
    @FXML
    public Label lblTroopName;
    @FXML
    public TextField txtTroopName;
    @FXML
    public TextField txtTroopNum;
    @FXML
    public TextField txtDenMother;
    @FXML
    public ComboBox<String> cbAgeLvl;
    @FXML
    public Button closeTroopViewBtn;
    @FXML
    public Button addTroopBtn;


    public void setModel(Model model) throws IOException {
        this.model = model;
        List<String> ageLevels = model.getAgeLvlList();
        cbAgeLvl.getItems().addAll(ageLevels);
    }

    @FXML
    public void closeView(MouseEvent mouseEvent) {
        Stage stage = (Stage) closeTroopViewBtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void createNewTroop(MouseEvent mouseEvent) throws InvalidTroopException{
        String tName = txtTroopName.getText();
        String tNum = txtTroopNum.getText();
        String denMother = txtDenMother.getText();
        String ageLvl = cbAgeLvl.getSelectionModel().getSelectedItem();

        try {
            Troop newTroop = new Troop(tName, denMother, ageLvl, Integer.parseInt(tNum));
            model.addTroop(newTroop);
            hasSaved = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Troop Created!");
            alert.setContentText("New Troop: " + tName + ", ID: " + tNum + " has been created.");
            alert.showAndWait();
            Stage stage = (Stage) addTroopBtn.getScene().getWindow();
            stage.close();
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Troop Created!");
            alert.setContentText("Troop Could Not Be Created:\n" + ex.getMessage());
            alert.showAndWait();
            Stage stage = (Stage) addTroopBtn.getScene().getWindow();
            stage.close();
        }
    }
}
