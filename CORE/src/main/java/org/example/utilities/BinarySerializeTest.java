package org.example.utilities;

import org.example.books.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BinarySerializeTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void serAndDeserBinary() throws IOException, ClassNotFoundException {
        Set<Book> allBooks = Book.deserializeCSV("randomBooks.csv");
        BinarySerialize.serializeAsBinary("randomBooksOutput.ser", allBooks);
        Object obj = BinarySerialize.deserializeBinary("randomBooksOutput.ser");
        assertEquals(allBooks, obj);
    }
}