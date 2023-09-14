module Bookshelf.APP {
    opens org.example.app to javafx.fxml;
    requires javafx.fxml;
    requires javafx.controls;
    requires Bookshelf.CORE;

    exports org.example.app;
}