package org.example.app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.books.Book;
import java.io.IOException;
import java.util.Set;


public class BookshelfApp extends javafx.application.Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * @param stage sets the primary stage, loads the fxml file, sets the scene, and shows the stage
     * also sets the sorting options for the sortingComboBox, sets the books in the bookListView,
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookshelf.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(true);
        stage.setTitle("Bookshelf");
        stage.setScene(scene);
        stage.show();

        BookshelfController controller = fxmlLoader.getController();
        ObservableList<String> sortingOptions = FXCollections.observableArrayList("Title", "Author", "Genre", "Publisher");
        controller.sortingComboBox.setItems(sortingOptions);
    }
}