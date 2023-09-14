package org.example.utilities;
import java.io.*;
import org.example.books.Book;
import java.util.Set;

public class BinarySerialize implements Serializable {

    public static void serializeAsBinary (String path, Object obj) throws IOException, FileNotFoundException {
        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(obj);
        out.close();
    }

    public static Object deserializeBinary (String path) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object obj = in.readObject();
        in.close();
        return obj;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Set<Book> allBooks = Book.deserializeCSV("randomBooks.csv");
        BinarySerialize.serializeAsBinary("randomBooksOutput.ser", allBooks);
        Object obj = BinarySerialize.deserializeBinary("randomBooksOutput.ser");
        System.out.println(obj);
    }
}
