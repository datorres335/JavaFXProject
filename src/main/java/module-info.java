module com.example.demojavafx3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.demojavafx3 to javafx.fxml;
    exports com.example.demojavafx3;
}