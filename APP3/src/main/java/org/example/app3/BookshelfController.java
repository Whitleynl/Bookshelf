package org.example.app3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class BookshelfController {
    public Label welcomeText;
    @FXML
    private ListView<String> bookListView;
    @FXML
    protected void handleStartButtonAction() {
        ObservableList<String>bookList = FXCollections.observableArrayList("Book 1", "Book 2", "Book 3");
        bookListView.setItems(bookList);
    }
}