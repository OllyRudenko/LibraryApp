package ua.olharudenko.libraryapp.service;

import ua.olharudenko.libraryapp.enums.OrderStatus;
import ua.olharudenko.libraryapp.models.Book;

import java.util.List;
import java.util.Optional;

public interface VisitorService {

    public Optional<Book> findByAuthor();

    public Optional<Book> findByTitle();

    public void makeOrder(OrderStatus orderStatus);
}
