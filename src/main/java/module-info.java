module com.brh.marsroverdownloader {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires json.simple;

    opens com.brh.marsroverdownloader to javafx.fxml;
    exports com.brh.marsroverdownloader;
}