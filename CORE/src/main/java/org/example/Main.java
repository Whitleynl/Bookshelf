package org.example;

import java.io.IOException;
import java.util.Set;

public class Main {
    public static void main(String[] args)  throws IOException {
        Set<Book> allBooks = Book.deserializeCSV("randomBooks.csv");
        Book.serializeAsCSV("randomBooksOutput.csv", allBooks);
    }
}
