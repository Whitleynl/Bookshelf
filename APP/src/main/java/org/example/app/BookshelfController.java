package org.example.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.books.Book;
import javafx.scene.control.Alert;

import java.util.*;

public class BookshelfController {
    public Label welcomeText;
    @FXML
    public TextArea bookDetailsTextArea;
    @FXML
    public Button addToList;
    @FXML
    public Button createNewList;
    @FXML
    private ListView<Book> bookListView;
    @FXML
    ComboBox<String> sortingComboBox;
    @FXML
    private TextField newListName;
    @FXML
    ListView<String> customListsView;

    private final Map<String, Set<Book>> customLists = new HashMap<>();
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

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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
     * sorts the bookListView based on the selected sorting option using a switch statement and the Comparator class
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
        updateBookListUI();
    }

    /**
     * triggers the handleBookSelectedAction method when the user presses the enter key
     */
    public void handleSortingAction() {
        String selectedSortingOption = sortingComboBox.getSelectionModel().getSelectedItem();
        sortBooks(selectedSortingOption);
    }

    private void updateBookListUI() {
        bookListView.refresh();
    }

    private void updateCustomListsUI() {
        customListsView.setItems(FXCollections.observableArrayList(customLists.keySet()));
        customListsView.refresh();
    }
    @FXML
    public void createNewList() {
        //this method needs to create a new custom list and add it to the customListsView
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create new List:");
        dialog.setHeaderText("Enter the name of your new list");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if(customLists.containsKey(name)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("List already exists");
                alert.setContentText("Please enter a different name for your new list");
                alert.showAndWait();
            }
            else {
                customLists.put(name, new TreeSet<>());
                updateCustomListView();
        }
        });
    }

    private void updateCustomListView() {
        customListsView.setItems(FXCollections.observableArrayList(customLists.keySet()));
        customListsView.refresh();
    }

    @FXML
    //this method needs to add the selected book to the user's custom list
    public void addToList(ActionEvent event) {
        Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
        String selectedList = customListsView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert(Alert.AlertType.ERROR, "Nothing Selected", "No book selected", "Please select a book to add to your custom list");
            return;
        }
        if (selectedList == null) {
            showAlert(Alert.AlertType.ERROR, "Nothing Selected", "No list selected", "Please select a list to add your book to");
            return;
        }
        Set<Book> booksForNewList = customLists.get(selectedList);
        booksForNewList.add(selectedBook);
        }
}