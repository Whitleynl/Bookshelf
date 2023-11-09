package org.example.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.books.Book;

public class CustomList extends Book {
    private final String name;
    private final ObservableList<String> books;

    public CustomList(String name) {
        this.name = name;
        this.books = FXCollections.observableArrayList();
    }

    public String getName() {
        return name;
    }

    public ObservableList<String> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return name;
    }
}
