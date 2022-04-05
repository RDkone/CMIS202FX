package com.example.cmis202fx;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GirlScoutView extends GridPane {

    private GirlScoutModel model;
    private GirlScoutController ctrl;
    private Button btn1, btn2, btn3, btn4, btn5, btn6;
    private RadioButton rb1, rb2;
    private TextArea txtA1;

    public GirlScoutView(GirlScoutController ctrl , GirlScoutModel model){
        this.ctrl = ctrl;
        this.model = model;
        createLayout();
    }

    public void createButtons(){
        btn1 = new Button("Create New Troop");
        btn2 = new Button("Assign Girl Scouts to Troop");
        btn3 = new Button("Display Existing Troops");
        btn4 = new Button("Display Girl Scouts in Troop");
        btn5 = new Button("Display a Girl Scout's Order");
        btn6 = new Button("Save/Load");
        btn1.setPrefSize(200, 20);
        btn2.setPrefSize(200, 20);
        btn3.setPrefSize(200, 20);
        btn4.setPrefSize(200, 20);
        btn5.setPrefSize(200, 20);

        ctrl.setAddTroopBtn(btn1);
        ctrl.setAssignGSTroopBtn(btn2);
        ctrl.setDisplayAllTroopsBtn(btn3);
        ctrl.setDisplayAllScoutsInTroopsBtn(btn4);
        ctrl.setDisplaySingleScoutOrderBtn(btn5);
        ctrl.setSaveloadSelectBtn(btn6);
    }
    public void createRadioButtons(){
        rb1 = new RadioButton("Save Project");
        rb2 = new RadioButton("Load Project");

        ctrl.setSaveBtn(rb1);
        ctrl.setLoadBtn(rb2);
    }

    public void createTextArea(){
        txtA1 = new TextArea();
        txtA1.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        txtA1.setPrefSize(250, 400);
        txtA1.setWrapText(true);
        txtA1.setEditable(false);
        ctrl.setTxtAreaDisplay(txtA1);
    }

    public void createLayout(){
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(5);
        setHgap(25);
        createButtons();
        createRadioButtons();
        createTextArea();
        ToggleGroup tg = new ToggleGroup();
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        ctrl.initialize();

        add(btn1, 0, 0);
        add(btn2, 0, 1);
        add(btn3, 0, 16);
        add(btn4, 0, 17);
        add(btn5, 0, 18);

        add(txtA1, 1, 0, 4, 20);
        add(rb1, 6, 6);
        add(rb2, 6, 7);
        add(btn6, 6, 10);


    }


}
