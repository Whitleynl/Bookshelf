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
    public Button createListButton;
    @FXML
    public ComboBox methodComboBox;
    @FXML
    public Button executeButton;
    @FXML
    public File selectedFile;
    public Label selectedFileNameLabel;
    @FXML
    private ListView<Book> bookListView;
    @FXML
    private TextField newListNameField;
    @FXML
    private ListView<CustomList> customListsView;
    @FXML
    ComboBox<String> sortingComboBox;
    @FXML
    private Label statusLabel;

    private final Set<Book> books = new TreeSet<>();

    private final Map<String, Book> titleToBookMap = new HashMap<>();

    @FXML
    private void initialize() {
        setUpBookListView();
        setUpCustomListsView();
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

    protected void setUpCustomListsView() {
        customListsView.setCellFactory(listView -> new ListCell<CustomList>() {
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
                System.out.println("drag accepted");
            }
            event.consume();
        });

        customListsView.setOnDragDropped(event -> {
            boolean success = false;
            Dragboard db = event.getDragboard();
            if (db.hasString()) {
                String bookTitle = db.getString();
                Book book = titleToBookMap.get(bookTitle);
                if (book != null) {
                    CustomList targetList = customListsView.getSelectionModel().getSelectedItem();
                    if (targetList != null) {
                        targetList.getBooks().add(book);
                        success = true;
                        System.out.println("book added to list");
                    }
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
        updateBookListUI(); //refreshes the bookListView
    }

    /**
     * triggers the handleBookSelectedAction method when the user presses the enter key
     */
    public void handleSortingAction() {
        String selectedSortingOption = sortingComboBox.getSelectionModel().getSelectedItem();
        sortBooks(selectedSortingOption);
    }

//    @FXML
//    private void handleCreateListAction() {
//        String newListName = newListNameField.getText().trim();
//        if (!newListName.isEmpty()) {
//            CustomList newList = new CustomList(newListName);
//            customListsView.getItems().add(newList);
//            newListNameField.clear();
//        }
//        else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Invalid List Name");
//            alert.setContentText("Please enter a valid list name.");
//            alert.showAndWait();
//        }
//    }

    @FXML
    private void handleOpenFileAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedFileNameLabel.setText(file.getName());
            System.out.println("File selected: " + file.getAbsolutePath());
        }
    }

    private void updateBookListUI() {
        bookListView.refresh();
    }

    @FXML
    private void handleMethodSelectionAction() {
        String selectedMethod = (String) methodComboBox.getSelectionModel().getSelectedItem();
        if (selectedMethod != null) {
            // Update the UI or set flags based on the selected method
            statusLabel.setText("Method selected: " + selectedMethod);

            // If you need to enable or disable buttons based on the selection
            // executeButton.setDisable(false);
        }
    }

    @FXML
    public void handleSerializeAction(ActionEvent actionEvent) throws IOException {
        Book.serializeAsCSV(selectedFile.getAbsolutePath(), books);
    }

    @FXML
    public void handleDeserializeAction(ActionEvent actionEvent) {
        Set<Book> deserializedBooks = Book.deserializeCSV(selectedFile.toPath());
    }
}