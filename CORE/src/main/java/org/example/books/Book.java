package org.example.books;
import org.cirdles.commons.util.ResourceExtractor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;


public class Book implements Comparable<Book>, Serializable {

    @Serial
    private static final long serialVersionUID = 7833299397228018065L;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }


    private String title;
    private String author;
    private String genre;
    private String publisher;

    private static final String MISSING_FIELD = "MISSING";

    public Book() {
        this(MISSING_FIELD, MISSING_FIELD, MISSING_FIELD, MISSING_FIELD);
    }

    public Book(String title, String author, String genre, String publisher) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
    }

    public static void serializeAsCSV(String path, Set<Book> books) throws IOException {
        Path filePath = Paths.get(path);
        if(filePath.toFile().exists()) {
            filePath.toFile().delete();
        }
        for (Book book : books) {
            byte[] strToBytes = book.prettyPrintCSV().getBytes();
            Files.write(filePath, strToBytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
    }

    public String prettyPrintCSV() {
        return title + ", " + author + ", " + genre + ", " + publisher + "\n";
    }

    public static Set<Book> deserializeCSV(Path filePath) {
        Set<Book> allBooks = new TreeSet<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath.toString()));
            for (String line : lines) {
                String[] fields = line.split(",");
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].trim().isBlank()) fields[i] = MISSING_FIELD;
                }
                Book book;
                if (fields.length == 4) {
                    book = new Book(fields[0].trim(), fields[1].trim(), fields[2].trim(), fields[3].trim());
                    allBooks.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allBooks;
    }

    public static Set<Book> deserializeCSV(String fileName){
        ResourceExtractor RESOURCE_EXTRACTOR = new ResourceExtractor(Book.class);
        Path resourcePath = RESOURCE_EXTRACTOR.extractResourceAsPath(fileName); //this line returns null
        return deserializeCSV(resourcePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, genre, publisher);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return title.equals(book.title) && author.equals(book.author) && genre.equals(book.genre) && publisher.equals(book.publisher);
    }
    @Override
    public int compareTo(Book o) {
        return -this.prettyPrintCSV().compareTo(o.prettyPrintCSV());
    }
}


