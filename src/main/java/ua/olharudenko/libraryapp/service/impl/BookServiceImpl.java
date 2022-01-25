package ua.olharudenko.libraryapp.service.impl;

import ua.olharudenko.libraryapp.dao.BookDAOImpl;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.service.BookService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {
    BookDAOImpl bookDAO = new BookDAOImpl();

    @Override
    public List<Book> getAllBooks() throws SQLException {
        return bookDAO.getAll();
    }

    @Override
    public List<Book> getAllBooksByAuthor(Long authorId) throws SQLException {
        List<Book> allBooks = bookDAO.getAllBooksByAuthor(authorId);
        Collections.sort(allBooks, Collections.reverseOrder());
        return allBooks;
    }

    @Override
    public Book save(Book book){
        Book savedBook = bookDAO.save(book);
        return savedBook;
    }
}
