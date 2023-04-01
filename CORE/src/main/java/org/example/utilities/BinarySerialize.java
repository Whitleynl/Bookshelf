package org.example.utilities;

import org.example.books.Book;

import java.io.*;
import java.util.Set;

public class BinarySerialize implements Serializable {

    //does it make more sense to have a package called "Serialize" and within it
    //you have different methods for different types of serialization?
    //or is it better to have a class for each type of serialization?

    //private static final long serialVersionUID = 1L; // don't know what this is yet
    //it belongs in Book class at the top of the class

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
