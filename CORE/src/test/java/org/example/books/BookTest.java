package org.example.books;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

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
        Set<Book> allBooks = Book.deserializeCSV("randomBooks.csv");
        Book.serializeAsCSV("test.csv", allBooks);
        Set<Book> books2 = Book.deserializeCSV(Paths.get("test.csv"));
        assertEquals(allBooks, books2);
    }
}