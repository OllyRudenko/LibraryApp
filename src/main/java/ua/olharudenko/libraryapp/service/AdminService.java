package ua.olharudenko.libraryapp.service;

import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.User;

import java.util.Optional;

public interface AdminService {

    public Optional<Book> addBook(Book book);

    public Optional<Book> update(Book book);

    public Optional<User> createLibrarian(User user);

    public Optional<User> deleteLibrarian(User user);

    public Optional<User> blockUser(User user);

    public Optional<User> unblockUser(User user);

    public Optional<User> confirmNewAdmin(User user);
}
