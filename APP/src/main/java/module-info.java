module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.example.core;

    opens com.example.app to javafx.fxml;
    exports com.example.app;
}



