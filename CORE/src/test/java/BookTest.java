package org.example;

import org.example.books.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void serializeAndDeserialize() throws IOException {
        Book book1 = new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", "Allen & Unwin");
        Set<Book> books = new TreeSet<>();
        books.add(book1);
        Book.serializeAsCSV("test.csv", books);
        Set<Book> books2 = Book.deserializeCSV("test.csv");
        assertEquals(books, books2);
    }
}