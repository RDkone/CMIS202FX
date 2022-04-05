module com.example.cmis202fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cmis202fx to javafx.fxml;
    exports com.example.cmis202fx;
}