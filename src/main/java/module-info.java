module com.example.demofx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.asartech.udhamFX to javafx.fxml;
    exports com.asartech.udhamFX;
}