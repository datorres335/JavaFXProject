module com.example.demojavafx3 {
    requires transitive javafx.graphics;
    //requires fr.brouillard.oss.cssfx;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.calendarfx.view;

    opens com.example.demojavafx3 to javafx.fxml;
    exports com.example.demojavafx3;
}