package ua.olharudenko.libraryapp.service;

import ua.olharudenko.libraryapp.enums.OrderStatus;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface VisitorService {

    public User registration(User user);

    public boolean isExistEmail(String email);

    public Optional<Book> findByAuthor();

    public Optional<Book> findByTitle();

    public void makeOrder(OrderStatus orderStatus);

    public List<Book> findBySearchWords(String searchWords) throws SQLException;
}
