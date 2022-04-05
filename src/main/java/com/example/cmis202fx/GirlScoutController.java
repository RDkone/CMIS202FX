package com.example.cmis202fx;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class GirlScoutController {
    private static GirlScoutModel gsModel;
    private static ScoutTroops troops;
    private static ObservableList<String> gsList;
    private static ObservableList<String> alList;


    private Button addTroopBtn;
    private Button assignGSTroopBtn;
    private Button displayAllTroopsBtn;
    private Button displayAllScoutsInTroopsBtn;
    private Button displaySingleScoutOrderBtn;
    private RadioButton saveBtn;
    private RadioButton loadBtn;
    private Button saveloadSelectBtn;
    private TextArea txtAreaDisplay;




    public GirlScoutController(GirlScoutModel model){
        gsModel = model;
    }
    public void initialize(){
        gsList = gsModel.getGirlScouts();
        alList = gsModel.getAgeLevels();
        gsList.addListener((ListChangeListener<String>) change -> {
            if (gsList.isEmpty()){
                assignGSTroopBtn.setDisable(true);
            }
        });
        addTroopBtn.setOnAction(e -> onButtonClicked(e));
        assignGSTroopBtn.setOnAction(e -> onButtonClicked(e));
        displayAllTroopsBtn.setOnAction(e -> onButtonClicked(e));
        displayAllScoutsInTroopsBtn.setOnAction(e -> onButtonClicked(e));
        displaySingleScoutOrderBtn.setOnAction(e -> onButtonClicked(e));
        saveloadSelectBtn.setOnAction(e -> onButtonClicked(e));

        troops = ScoutTroops.getScoutTroopsSingleInstance();
    }


    public void setAddTroopBtn(Button addTroopBtn) {
        this.addTroopBtn = addTroopBtn;
    }
    public void setAssignGSTroopBtn(Button assignGSTroopBtn) {
        this.assignGSTroopBtn = assignGSTroopBtn;
    }

    public void setDisplayAllTroopsBtn(Button displayAllTroopsBtn) {
        this.displayAllTroopsBtn = displayAllTroopsBtn;
    }

    public void setDisplayAllScoutsInTroopsBtn(Button displayAllScoutsInTroopsBtn) {
        this.displayAllScoutsInTroopsBtn = displayAllScoutsInTroopsBtn;
    }

    public void setDisplaySingleScoutOrderBtn(Button displaySingleScoutOrderBtn) {
        this.displaySingleScoutOrderBtn = displaySingleScoutOrderBtn;
    }

    public void setSaveBtn(RadioButton saveBtn) {
        this.saveBtn = saveBtn;
    }

    public void setLoadBtn(RadioButton loadBtn) {
        this.loadBtn = loadBtn;
    }

    public void setSaveloadSelectBtn(Button saveloadSelectBtn) {
        this.saveloadSelectBtn = saveloadSelectBtn;
    }

    public void setTxtAreaDisplay(TextArea txtAreaDisplay) {
        this.txtAreaDisplay = txtAreaDisplay;
    }

    public void onButtonClicked(ActionEvent e){
        if (e.getSource().equals(addTroopBtn)){
            Troop troop = AddTroopBox.display("Create Troop", "Create New Troop");
            if (troop != null){
                troops.addTroop(troop);
            }
        }
        if (e.getSource().equals(assignGSTroopBtn)){
            GirlScout girlScout = AssignScoutBox.display("Girl Scout", "Assign Girl Scout");
            if (girlScout != null){
                girlScout.addScoutToTroop();
            }
        }
        if (e.getSource().equals(displayAllTroopsBtn)){
            txtAreaDisplay.clear();
            txtAreaDisplay.setText("All Existing Troops:\n" +
                   "------------------------------------\n" +troops.listTroops());
        }
        if (e.getSource().equals(displayAllScoutsInTroopsBtn)){
            txtAreaDisplay.clear();
            int trpNum = TrpNumBox.display("Troop", "Input Troop Number");
            HashMap<Integer, Troop> hm = (HashMap<Integer, Troop>) troops.getTroopsList();
            Set<Integer> set = hm.keySet();
            for (int i : set){
                if (i == trpNum){
                    txtAreaDisplay.setText("All of the Girl Scouts in Troop " + trpNum + ":" +
                             "\n" + "---------------" + "\n" + hm.get(i).listTroopMembers());
                }
            }
        }
        if (e.getSource().equals(displaySingleScoutOrderBtn)){
            txtAreaDisplay.clear();
            String scoutName = ScoutNameBox.display("Girl Scout", "Input Girl Scout Name");
            int trpNum = TrpNumBox.display("Troop", "Input Troop Number");
            HashMap<Integer, Troop> hm = troops.getTroopsList();
            Set<Integer> set = hm.keySet();
            for (int i : set){
                if (i == trpNum){
                    List<GirlScout> scoutList = hm.get(i).getTroopMembers();
                    for (GirlScout g : scoutList){
                        if (g.getScoutName().equalsIgnoreCase(scoutName)){
                            CookieOrderManager manager = g.getCookieOrderManager();
                            double totalAmountSold = manager.getTotalAmountOfOrders();
                            String allOrders = manager.displayAllOrders();
                            String singleScoutOrder = scoutName + "'s total sales amount to: $" + totalAmountSold + "0" +
                                    "\n" + "\n" + "Here's all the Cookie Orders of " + scoutName + "." + "\n" + allOrders;
                            txtAreaDisplay.setText(singleScoutOrder);
                        }
                    }
                }
            }
        }
        if (e.getSource().equals(saveloadSelectBtn)){
            if (saveBtn.isSelected()){
                String fileName = "GirlScoutProj.dat";
                troops.exportAll(fileName);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Project Saved. Do you wish to exit the program?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()){
                    if (result.get() == ButtonType.OK){
                        System.exit(2);
                    }
                }

            }

            if (loadBtn.isSelected()){
                Path currentRelPath = Paths.get("");
                String initDir = currentRelPath.toAbsolutePath().toString();
                File dir = new File(initDir);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(dir);
                fileChooser.setTitle("Load Project");
                File selectedFile = fileChooser.showOpenDialog(null);

                if (selectedFile != null){
                    troops = ScoutTroops.importAll(selectedFile.getName());
                    HashMap<Integer, Troop> hm = troops.getTroopsList();
                    Set<Integer> set = hm.keySet();
                    for (int i : set){
                        List<GirlScout> currentGSList = hm.get(i).getTroopMembers();
                        for (GirlScout g : currentGSList){
                            gsModel.removeGirlScoutOnceAssigned(g.getScoutName());
                        }


                    }
                }
                else {
                    System.out.println("Selected File is Not Valid.");
                }
            }
        }
    }

    static class AddTroopBox {
        static Troop troop;
        public static Troop display(String title, String msg){
            Stage win = new Stage();
            win.initModality(Modality.APPLICATION_MODAL);
            win.setTitle(title);
            win.setMinWidth(450);
            win.setMinHeight(150);
            Label addTroopLBL = new Label();
            addTroopLBL.setText(msg);

            Label troopNameLBL = new Label("Troop Name: ");
            Label denMotherLBL = new Label("Den Mother: ");
            Label ageLevelLBL = new Label("Age Level: ");
            Label troopNumberLBL = new Label("Troop Number: ");
            TextField txtTName = new TextField();
            TextField txtDMother = new TextField();
            //TextField txtAgeLvl = new TextField();
            ChoiceBox<String> cbAgeLvl = new ChoiceBox<String>(alList);
            TextField txtTNumber = new TextField();
            txtTName.setMaxWidth(250);
            txtDMother.setMaxWidth(250);
            //txtAgeLvl.setMaxWidth(250);
            cbAgeLvl.setMaxWidth(250);
            txtTNumber.setMaxWidth(250);
            Button createTroopBtn = new Button("Create Troop");

            createTroopBtn.setOnAction(e -> {
                String trpName = txtTName.getText(),
                        denMot = txtDMother.getText(),
                        ageLvl = cbAgeLvl.getValue();
                int trpNumber = Integer.parseInt(txtTNumber.getText());

                try {
                    troop = new Troop(trpName, denMot, ageLvl, trpNumber);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Troop " + trpName + " has been created." +
                            "\nThe Den Mother is " + denMot +
                            "\nThe Age Level is " + ageLvl +
                            "\nThe Troop Number is " + trpNumber);
                    alert.show();
                    win.close();

                } catch (InvalidTroopException invExc){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(invExc.getMessage());
                    alert.show();
                    win.close();
                }

            });

            BorderPane boxLayout = new BorderPane();
            VBox txtLabels = new VBox(17);
            txtLabels.getChildren().addAll(troopNameLBL, denMotherLBL, ageLevelLBL, troopNumberLBL);
            VBox txtFields = new VBox(7);
            txtFields.getChildren().addAll(txtTName, txtDMother, cbAgeLvl, txtTNumber);
            boxLayout.setLeft(txtLabels);
            boxLayout.setTop(addTroopLBL);
            boxLayout.setCenter(txtFields);
            boxLayout.setBottom(createTroopBtn);
            BorderPane.setAlignment(addTroopLBL, Pos.TOP_CENTER);
            BorderPane.setMargin(txtFields, new Insets(5));
            BorderPane.setAlignment(createTroopBtn, Pos.BOTTOM_CENTER);



            Scene scene = new Scene(boxLayout);
            win.setScene(scene);
            win.setResizable(false);
            win.showAndWait();

            return troop;
        }
    }

    static class AssignScoutBox {
        static GirlScout girlScout;
        public static GirlScout display(String title, String msg){
            Stage win  = new Stage();
            win.initModality(Modality.APPLICATION_MODAL);
            win.setTitle(title);
            win.setMinWidth(400);
            Label assignLBL = new Label();
            assignLBL.setText(msg);

            ChoiceBox<String> scouts = new ChoiceBox<>(gsList);
            Label txtAssignLBL = new Label();
            txtAssignLBL.setText("Select Scout to Assign to Troop (Troop number):");
            TextField txtAssign = new TextField();
            txtAssign.setMaxWidth(250);
            Button assignBtn = new Button("Assign Girl Scout to Troop");

            assignBtn.setOnAction(e -> {
                String getGSName = scouts.getValue();
                int getTrpNum = Integer.parseInt(txtAssign.getText());
                try {
                    girlScout = new GirlScout(getGSName, getTrpNum);
                    switch (getGSName){
                        case "Elise":
                            CookieOrderManager EliseManager = girlScout.getCookieOrderManager();
                            EliseOrders(EliseManager);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText(getGSName + " has been assigned to Troop# " + getTrpNum);
                            break;
                        case "Jill":
                            CookieOrderManager JillManager = girlScout.getCookieOrderManager();
                            JillOrders(JillManager);
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setContentText(getGSName + " has been assigned to Troop# " + getTrpNum);
                            break;
                        case "Lucy":
                            CookieOrderManager LucyManager = girlScout.getCookieOrderManager();
                            LucyOrders(LucyManager);
                            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                            alert3.setContentText(getGSName + " has been assigned to Troop# " + getTrpNum);
                            break;
                        case "Maddie":
                            CookieOrderManager MaddieManager = girlScout.getCookieOrderManager();
                            MaddieOrders(MaddieManager);
                            Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
                            alert4.setContentText(getGSName + " has been assigned to Troop# " + getTrpNum);
                            break;
                        case "Mary":
                            CookieOrderManager MaryManager = girlScout.getCookieOrderManager();
                            MaryOrders(MaryManager);
                            Alert alert5 = new Alert(Alert.AlertType.INFORMATION);
                            alert5.setContentText(getGSName + " has been assigned to Troop# " + getTrpNum);
                            break;
                        case "Nora":
                            CookieOrderManager NoraManager = girlScout.getCookieOrderManager();
                            NoraOrders(NoraManager);
                            Alert alert6 = new Alert(Alert.AlertType.INFORMATION);
                            alert6.setContentText(getGSName + " has been assigned to Troop# " + getTrpNum);
                            break;
                        case "Penny":
                            CookieOrderManager PennyManager = girlScout.getCookieOrderManager();
                            PennyOrders(PennyManager);
                            Alert alert7 = new Alert(Alert.AlertType.INFORMATION);
                            alert7.setContentText(getGSName + " has been assigned to Troop# " + getTrpNum);
                            break;
                        case "Sandra":
                            CookieOrderManager SandraManager = girlScout.getCookieOrderManager();
                            SandraOrders(SandraManager);
                            Alert alert8 = new Alert(Alert.AlertType.INFORMATION);
                            alert8.setContentText(getGSName + " has been assigned to Troop# " + getTrpNum);
                            break;
                    }

                    gsModel.removeGirlScoutOnceAssigned(getGSName);
                    win.close();

                } catch (InvalidTroopException invExc){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(invExc.getMessage());
                    alert.show();
                    win.close();
                }
            });

            VBox boxLayout = new VBox(10);
            boxLayout.getChildren().addAll(assignLBL, scouts, txtAssignLBL, txtAssign, assignBtn);
            boxLayout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(boxLayout);
            win.setScene(scene);
            win.setResizable(false);
            win.showAndWait();

            return girlScout;

        }
        public static void EliseOrders(CookieOrderManager manager){
            manager.addOrder("Alice Perez", Cookie.SAMOAS, 3);
            manager.addOrder("Juan Flores", Cookie.THIN_MINTS, 6);
            manager.addOrder("Carl Edwards", Cookie.TAGALONGS, 5);
            manager.addOrder("Kathy Woods", Cookie.LEMONADES, 4);
        }
        public static void JillOrders(CookieOrderManager manager){
            manager.addOrder("Susan Brooks", Cookie.THIN_MINTS, 3);
            manager.addOrder("Barbara Powell", Cookie.THIN_MINTS, 9);
            manager.addOrder("Pamela Gray", Cookie.CARAMEL_CHOCOLATE_CHIP, 5);
            manager.addOrder("Richard Garcia", Cookie.LEMONADES, 2);
        }
        public static void LucyOrders(CookieOrderManager manager){
            manager.addOrder("Alice Perez", Cookie.SAMOAS, 3);
            manager.addOrder("Juan Flores", Cookie.THIN_MINTS, 2);
            manager.addOrder("Carl Edwards", Cookie.TAGALONGS, 5);
            manager.addOrder("Kathy Woods", Cookie.LEMONADES, 7);
        }
        public static void MaddieOrders(CookieOrderManager manager){
            manager.addOrder("Ernest Hughes", Cookie.TOFFEE_TASTIC, 4);
            manager.addOrder("Lori Hall", Cookie.LEMONUPS, 3);
            manager.addOrder("Arthur Barnes", Cookie.SAMOAS, 1);
            manager.addOrder("Walter Davis", Cookie.DO_SI_DOS, 6);
        }
        public static void MaryOrders(CookieOrderManager manager){
            manager.addOrder("Bobby Cook", Cookie.TAGALONGS, 6);
            manager.addOrder("Peter Parry", Cookie.THIN_MINTS, 3);
            manager.addOrder("Melissa Gonzales", Cookie.SAMOAS, 2);
            manager.addOrder("Brandon Garcia", Cookie.TREFOILS, 8);
        }
        public static void NoraOrders(CookieOrderManager manager){
            manager.addOrder("William Foster", Cookie.LEMONUPS, 2);
            manager.addOrder("Jack Edwards", Cookie.TAGALONGS, 7);
            manager.addOrder("Diana Cox", Cookie.CARAMEL_CHOCOLATE_CHIP, 8);
            manager.addOrder("Jesse Vaxsin", Cookie.TOFFEE_TASTIC, 5);
        }
        public static void PennyOrders(CookieOrderManager manager){
            manager.addOrder("Craig Brooks", Cookie.TOFFEE_TASTIC, 9);
            manager.addOrder("Gloria Campbell", Cookie.CARAMEL_CHOCOLATE_CHIP, 2);
            manager.addOrder("Joseph James", Cookie.TAGALONGS, 5);
            manager.addOrder("Todd Sanchez", Cookie.LEMONUPS, 6);
        }
        public static void SandraOrders(CookieOrderManager manager){
            manager.addOrder("Tina Taylor", Cookie.THIN_MINTS, 3);
            manager.addOrder("Rachel Thomas", Cookie.TREFOILS, 7);
            manager.addOrder("Alice Jones", Cookie.THIN_MINTS, 2);
            manager.addOrder("Emily Parker", Cookie.DO_SI_DOS, 4);
        }


    }

    static class TrpNumBox {
        static int trpNumber;
        public static int display(String title, String msg){
            Stage win = new Stage();
            win.initModality(Modality.APPLICATION_MODAL);
            win.setTitle(title);
            win.setMinWidth(450);
            Label displayTrpNumLBL = new Label();
            displayTrpNumLBL.setText(msg);

            Label lblTrpNum = new Label("Troop Number:");
            TextField txtTrpNum = new TextField();
            txtTrpNum.setMaxWidth(250);
            Button btnGetTrpNum = new Button("Submit");
            btnGetTrpNum.setOnAction(e -> {
                try {
                    trpNumber = Integer.parseInt(txtTrpNum.getText());
                    win.close();

                } catch (Exception exc){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(exc.getMessage());
                    alert.show();
                    win.close();
                }
            });

            VBox boxLayout = new VBox(10);
            boxLayout.getChildren().addAll(displayTrpNumLBL, lblTrpNum, txtTrpNum, btnGetTrpNum);
            boxLayout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(boxLayout);
            win.setScene(scene);
            win.setResizable(false);
            win.showAndWait();

            return trpNumber;
        }
    }

    static class ScoutNameBox {
        static String scoutName;
        public static String display(String title, String msg){
            Stage win = new Stage();
            win.initModality(Modality.APPLICATION_MODAL);
            win.setTitle(title);
            win.setMinWidth(450);
            Label displayScoutNmLBL = new Label();
            displayScoutNmLBL.setText(msg);

            Label lblScoutNm = new Label("Scout Name:");
            TextField txtScoutNm = new TextField();
            txtScoutNm.setMaxWidth(250);
            Button btnGetScoutNm = new Button("Submit");
            btnGetScoutNm.setOnAction(e -> {
                try {
                    scoutName = txtScoutNm.getText();
                    win.close();

                } catch (Exception exc){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(exc.getMessage());
                    alert.show();
                    win.close();
                }
            });

            VBox boxLayout = new VBox(10);
            boxLayout.getChildren().addAll(displayScoutNmLBL, lblScoutNm, txtScoutNm, btnGetScoutNm);
            boxLayout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(boxLayout);
            win.setScene(scene);
            win.setResizable(false);
            win.showAndWait();

            return scoutName;
        }
    }

}
