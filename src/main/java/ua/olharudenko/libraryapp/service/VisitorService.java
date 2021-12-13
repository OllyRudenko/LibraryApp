package ua.olharudenko.libraryapp.service;

import ua.olharudenko.libraryapp.enums.OrderStatus;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.Penalty;

import java.util.List;
import java.util.Optional;

public interface VisitorService {

    public Optional<Book> findByAuthor();

    public Optional<Book> findByTitle();

    public void makeOrder(OrderStatus orderStatus);

    public List<Penalty> checkPenalty(Long userId);
}
