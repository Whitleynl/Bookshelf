package org.example.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.books.Book;
import java.util.Comparator;
import java.util.Set;

public class BookshelfController {
    public Label welcomeText;
    @FXML
    public TextArea bookDetailsTextArea;
    public Button createListButton;
    @FXML
    private ListView<Book> bookListView;

    @FXML
    private TextField newListNameField;

    @FXML
    private ListView<String> customListsView;
    @FXML
    ComboBox<String> sortingComboBox;

    public void initialize() {
        bookListView.setCellFactory(bookListView -> new ListCell<>() {
            /**
             * @param book  The new item for the cell.
             * @param empty whether this cell represents data from the list. If empty,
             *              will appear as an empty cell.
             */
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null || book.getTitle() == null) {
                    setText(null);
                } else {
                    setText(book.getTitle());
                }
            }
        });
    }

    /**
     * @param books converts the set of books to an observable list and sets the bookListView to the observable list
     */
    @FXML
    public void setBooks(Set<Book> books) {
        ObservableList<Book> bookList = FXCollections.observableArrayList(books);
        bookListView.setItems(bookList);
    }

    public void handleExitButtonAction() {
        System.exit(0);
    }

    /**
     * gets the selected book from the bookListView and sets the bookDetailsTextArea to the selected book's details
     */
    public void handleBookSelectedAction() {
        Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookDetailsTextArea.setText("Title: " + selectedBook.getTitle() + "\n" +
                    "Author: " + selectedBook.getAuthor() + "\n" +
                    "Genre: " + selectedBook.getGenre() + "\n" +
                    "Publisher: " + selectedBook.getPublisher() + "\n");
        }
    }

    /**
     * @param selectedSortingOption the selected sorting option from the sortingComboBox
     *                              sorts the bookListView based on the selected sorting option using a switch statement and the Comparator class
     */
    //this still needs to account for articles in the titles (a, an, the)
    public void sortBooks(String selectedSortingOption) {
        if (selectedSortingOption == null) {
            return;
        }
        switch (selectedSortingOption) {
            case "Author" -> bookListView.getItems().sort(Comparator.comparing(Book::getAuthor));
            case "Genre" -> bookListView.getItems().sort(Comparator.comparing(Book::getGenre));
            case "Publisher" -> bookListView.getItems().sort(Comparator.comparing(Book::getPublisher));
            default -> bookListView.getItems().sort(Comparator.comparing(Book::getTitle));
        }
        updateBookListUI(); //refreshes the bookListView
    }

    /**
     * triggers the handleBookSelectedAction method when the user presses the enter key
     */
    public void handleSortingAction() {
        String selectedSortingOption = sortingComboBox.getSelectionModel().getSelectedItem();
        sortBooks(selectedSortingOption);
    }

    @FXML
    private void handleCreateListAction() {
        if (!newListNameField.getText().isEmpty()) {
            customListsView.getItems().add(newListNameField.getText());
            newListNameField.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No list name entered");
            alert.setContentText("Please enter a name for the new list");
            alert.showAndWait();
        }
    }


    private void updateBookListUI() {
        bookListView.refresh();
    }
}