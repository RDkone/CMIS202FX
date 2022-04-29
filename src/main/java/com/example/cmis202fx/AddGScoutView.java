package com.example.cmis202fx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.example.cmis202fx.Controller.hasSaved;

public class AddGScoutView {
    private Model model;
    @FXML
    private TextField txtGSName;
    @FXML
    private TextField txtTroopNameNum;
    @FXML
    private Button assignGSBtn;
    @FXML
    private Button cancelBtn;

    public void setModel(Model model){
        this.model = model;
    }

    @FXML
    public void closeView(MouseEvent mouseEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void AddAssignScout(MouseEvent event) throws InvalidTroopException {
        GirlScout newScout;
        try {
            String scoutName = txtGSName.getText();
            String troopAssign = txtTroopNameNum.getText();
            if (troopAssign.matches("[0-9]+")){
                newScout = new GirlScout(scoutName, Integer.parseInt(troopAssign));
            }
            else {
                newScout = new GirlScout(scoutName, troopAssign);
            }
            newScout.addScoutToTroop();
            model.createdGS(newScout);
            hasSaved = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Girl Scout Created & Assigned");
            alert.setContentText(newScout.toString());
            alert.showAndWait();
            Stage stage = (Stage) assignGSBtn.getScene().getWindow();
            stage.close();
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Creating and Assigning Girl Scout");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

}
