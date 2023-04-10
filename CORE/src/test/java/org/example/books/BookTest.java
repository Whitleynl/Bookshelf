package org.example.books;
import org.cirdles.commons.util.ResourceExtractor;
import org.example.books.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
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
        ResourceExtractor tripoliExtractor = new ResourceExtractor(Book.class);
        File filename = tripoliExtractor.extractResourceAsFile("/org/example/books/randomBooks.csv");
        Set<Book> allBooks = Book.deserializeCSV("randomBooks.csv");
        Book.serializeAsCSV("randomBooksOutput.csv", allBooks);
        Book book1 = new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", "Allen & Unwin");
        Set<Book> books = new TreeSet<>();
        books.add(book1);
        Book.serializeAsCSV("test.csv", books);
        Set<Book> books2 = Book.deserializeCSV("test.csv");
        assertEquals(books, books2);
    }
}