package com.example.cmis202fx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TroopView {
    @FXML
    private Label lblTroopName;
    @FXML
    private Label lblID;
    @FXML
    private Label lblDenMother;
    @FXML
    private Label lblAgeLvl;
    @FXML
    private Label lblGS;
    @FXML
    private ListView<String> gsLV;
    @FXML
    private Button closeTroopViewBtn;

    public void setTroop(Troop troop){
        lblTroopName.setText(troop.getTroopName());
        lblID.setText(String.valueOf(troop.getTroopNumber()));
        lblDenMother.setText(troop.getDenMother());
        lblAgeLvl.setText(troop.getAgeLevel());
        if (troop.getTroopMembers().size() > 0){
            gsLV.getItems().addAll(troop.listTroopMemberNames());
            gsLV.setVisible(true);
            gsLV.setMouseTransparent(true);
            gsLV.setFocusTraversable(false);
            lblGS.setVisible(false);
        }
        else {
            lblGS.setText("N/A");
            lblGS.setVisible(true);
            gsLV.setVisible(false);
        }
    }

    @FXML
    private void closeTroopView(MouseEvent event){
        Stage stage = (Stage) closeTroopViewBtn.getScene().getWindow();
        stage.close();
    }

}
