package ua.olharudenko.libraryapp.service;

import ua.olharudenko.libraryapp.models.Book;

import java.util.List;

public interface CatalogService {

    public List<Book> findAll();

    // to do implements Comparable<Book> or??
    //    List<Book> sortedBook = books.stream()
    //            .sorted(Comparator.comparing(Book::getTitle).reversed())
    //            .collect(Collectors.toList()); etc.
    public List<Book> sortBy();

    public List<Book> findAllByTitle(String title);

    public List<Book> findAllByAuthor(String author);
}
