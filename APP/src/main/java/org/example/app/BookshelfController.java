package org.example.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import org.example.books.Book;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BookshelfController {
    public Label welcomeText;
    @FXML
    public TextArea bookDetailsTextArea;
    @FXML
    public File selectedFile;
    public Label selectedFileNameLabel;
    @FXML
    private ListView<Book> bookListView;
    @FXML
    ComboBox<String> sortingComboBox;
    @FXML
    private Button serializeButton;
    @FXML
    private Button deserializeButton;
    @FXML
    private TextArea filePreviewTextArea;
    private final Set<Book> books = new TreeSet<>();
    private final Map<String, Book> titleToBookMap = new HashMap<>();

    @FXML
    private void initialize() {
        setUpBookListView();
    }

    protected void setUpBookListView() {
        bookListView.setCellFactory(listView -> new ListCell<>() {
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

    /**
     * @param books converts the set of books to an observable list and sets the bookListView to the observable list
     */
    @FXML
    public void setBooks(Set<Book> books) {
        ObservableList<Book> bookList = FXCollections.observableArrayList(books);
        bookListView.setItems(bookList);
        bookListView.refresh();
        for (Book book : books) {
            titleToBookMap.put(book.getTitle(), book);
        }
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
        updateBookListUI();
    }

    /**
     * triggers the handleBookSelectedAction method when the user presses the enter key
     */
    public void handleSortingAction() {
        String selectedSortingOption = sortingComboBox.getSelectionModel().getSelectedItem();
        sortBooks(selectedSortingOption);
    }


    @FXML
    private void handleOpenFileAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedFileNameLabel.setText(file.getName());
            serializeButton.setDisable(false);
            deserializeButton.setDisable(false);
            selectedFile = file;
        }
    }

    private void updateBookListUI() {
        bookListView.refresh();
    }

    @FXML
    public void handleSerializeAction(ActionEvent actionEvent) throws IOException {
        Book.serializeAsCSV(selectedFile.getAbsolutePath(), books);
    }

    @FXML
    public void handleDeserializeAction(ActionEvent actionEvent) {
        Set<Book> deserializedBooks = Book.deserializeCSV(selectedFile.toPath());
        books.addAll(deserializedBooks);
        setBooks(books);
        updateBookListUI();
    }
}