package org.example.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomList {
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
}
