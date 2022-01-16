package ua.olharudenko.libraryapp.service;

import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.User;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    public List<User> createLibrarian(List<Long> ids);

    public Optional<User> deleteLibrarian(User user);

    public Optional<User> blockUser(User user);

    public Optional<User> unblockUser(User user);

    public List<User> confirmNewAdmin(List<Long> ids);

    public List<User> backVisitorRole(List<Long> ids);
}
