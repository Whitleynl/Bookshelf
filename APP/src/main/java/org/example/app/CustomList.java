package org.example.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.books.Book;

public class CustomList  {
    private final String name;
    private final ObservableList<Book> books;

    public CustomList(String name) {
        this.name = name;
        this.books = FXCollections.observableArrayList();
    }

    public String getName() {
        return name;
    }

    public ObservableList<Book> getBooks() {
        return books;
    }

    public String toString() {
        return name;
    }
}
