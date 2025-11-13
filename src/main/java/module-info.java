module fr.esgi.tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires org.json;
    //requires fr.esgi.tracker;

    opens fr.esgi.tracker to javafx.fxml;
    opens fr.esgi.tracker.controller to javafx.fxml;
    //opens fr.esgi.tracker.dto to javafx.base;
    exports fr.esgi.tracker;
}
