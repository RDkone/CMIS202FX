package com.example.cmis202fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectApp  extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ProjectView.fxml"));
        Scene scene = new Scene(loader.load());
        Controller controller = loader.getController();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            boolean exitProgram = controller.handleSystemExit();
            if (!exitProgram){
                windowEvent.consume();
            }

        });
    }
    public static void main(String[] args) {
        launch();
    }
}
