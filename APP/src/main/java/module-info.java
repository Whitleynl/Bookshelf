module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.example.core;
    requires kotlin.stdlib;

    opens org.example.app to javafx.fxml;
    opens org.example to javafx.fxml;
    exports org.example.app;
}



