package org.example.app3;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BookshelfApp extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BookshelfApp.class.getResource("bookshelf.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Bookshelf");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        javafx.application.Application.launch(args);
    }
}