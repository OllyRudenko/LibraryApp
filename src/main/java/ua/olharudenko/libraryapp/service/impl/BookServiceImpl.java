package ua.olharudenko.libraryapp.service.impl;

import ua.olharudenko.libraryapp.dao.BookDAOImpl;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.service.BookService;

import java.sql.SQLException;
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
        return bookDAO.getAllBooksByAuthor(authorId);
    }

    @Override
    public Book save(Book book){
        Book savedBook = bookDAO.save(book);
        return savedBook;
    }
}
