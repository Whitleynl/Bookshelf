package org.example.books

import org.junit.jupiter.api.Test
import java.io.FileInputStream
import java.io.InputStream

class BookTestKT {

    @Test
    fun readCSV() {
        val inputStream = FileInputStream("../CORE/src/test/resources/randomBooks.csv")
        val bookList = readCSVFile(inputStream)
        val others = bookList

    }

    @Test
    fun readCSVFile(inputStream: InputStream): List<Book> {
        val reader = inputStream.bufferedReader()
        val header = reader.readLine()
        return reader.lineSequence().filter { it.isNotBlank() }.map {
            val (title, author, publisher) = it.split(',', ignoreCase = false, limit = 3)
            Book(title.trim(), author.trim(), publisher.trim())
        }.toList()
    }


    data class Book(
            val title: String,
            val author: String,
            val publisher: String
    )

}