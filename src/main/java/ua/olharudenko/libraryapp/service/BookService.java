package ua.olharudenko.libraryapp.service;

import ua.olharudenko.libraryapp.models.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BookService {

    public List<Book> getAllBooks() throws SQLException;

    public List<Book> getAllBooksByAuthor(Long authorId) throws SQLException;
}
