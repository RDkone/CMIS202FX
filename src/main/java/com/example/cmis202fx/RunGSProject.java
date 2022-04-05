package com.example.cmis202fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunGSProject extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GirlScoutModel model = new GirlScoutModel();
        GirlScoutController ctrl = new GirlScoutController(model);
        GirlScoutView view = new GirlScoutView(ctrl, model);
        Scene scene = new Scene(view, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Girl Scout Manager");
        primaryStage.show();
        primaryStage.setResizable(false);
    }
}
