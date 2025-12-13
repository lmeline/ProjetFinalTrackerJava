module fr.esgi.tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires javafx.base;
    requires javafx.graphics;
    requires com.google.gson;

    opens fr.esgi.tracker to javafx.fxml;
    opens fr.esgi.tracker.controller to javafx.fxml;
    opens fr.esgi.tracker.business to com.google.gson;

    exports fr.esgi.tracker;
}