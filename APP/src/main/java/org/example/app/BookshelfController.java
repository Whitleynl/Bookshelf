package org.example.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.example.books.Book;
import java.util.Comparator;
import java.util.Set;

public class BookshelfController {
    public Label welcomeText;
    @FXML
    public TextArea bookDetailsTextArea;
    @FXML
    public Button createListButton;
    @FXML
    private ListView<Book> bookListView;
    @FXML
    private TextField newListNameField;
    @FXML
    private ListView<CustomList> customListsView;
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
                bookListView.setOnDragDetected(event -> {
                    if (bookListView.getSelectionModel().getSelectedItem() != null) {
                        Dragboard db = bookListView.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        content.putString(bookListView.getSelectionModel().getSelectedItem().getTitle());
                        db.setContent(content);
                        event.consume();
                    }
                });
            }
        });

        customListsView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(CustomList item, boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        customListsView.setOnDragOver(event -> {
            if (event.getGestureSource() != customListsView &&
                    event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        customListsView.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String book = db.getString();
                CustomList targetList = customListsView.getSelectionModel().getSelectedItem();
                if (targetList != null) {
                    targetList.getBooks().add(book);
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
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
        String newListName = newListNameField.getText().trim();
        if (!newListName.isEmpty()) {
            CustomList newList = new CustomList(newListName);
            customListsView.getItems().add(newList);
            newListNameField.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid List Name");
            alert.setContentText("Please enter a valid list name.");
            alert.showAndWait();
        }
    }


    private void updateBookListUI() {
        bookListView.refresh();
    }
}