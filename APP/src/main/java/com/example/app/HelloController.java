package com.example.app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.books.Book;

import java.util.Set;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
            welcomeText.setText("Welcome to Bookshelf!");
            Set<Book> books = Book.deserializeCSV("randomBooks.csv");
            for (Book book : books) {
                welcomeText.setText(book.prettyPrintCSV());

        }
    }
}