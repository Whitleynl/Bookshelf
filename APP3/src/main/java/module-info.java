module org.example.app3 {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens org.example.app3 to javafx.fxml;
    exports org.example.app3;
}