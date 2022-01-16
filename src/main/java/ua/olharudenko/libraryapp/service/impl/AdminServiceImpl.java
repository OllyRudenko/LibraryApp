package ua.olharudenko.libraryapp.service.impl;

import ua.olharudenko.libraryapp.dao.UserDAOImpl;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.service.AdminService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminServiceImpl implements AdminService {
    UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public List<User> createLibrarian(List<Long> ids) {
        List<User> librarians = new ArrayList<User>();
        ids.stream().forEach(i ->
        {
            if (changeToLibrarianRole(i).isPresent()) {
                librarians.add(changeToLibrarianRole(i).get());
            }
        });
        return librarians;
    }

    private Optional<User> changeToLibrarianRole(Long id) {
        Optional<User> user = null;
        try {
            user = userDAO.get(id);
            if (user.isPresent()) {
                user.get().setRole(Role.LIBRARIAN);
                userDAO.update(user.get());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Optional<User> deleteLibrarian(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> blockUser(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> unblockUser(User user) {
        return Optional.empty();
    }

    @Override
    public List<User> confirmNewAdmin(List<Long> ids) {
        List<User> admins = new ArrayList<User>();
        ids.stream().forEach(i ->
        {
            if (changeToAdminRole(i).isPresent()) {
                admins.add(changeToAdminRole(i).get());
            }
        });
        return admins;
    }

    private Optional<User> changeToAdminRole(Long id) {
        Optional<User> user = null;
        try {
            user = userDAO.get(id);
            if (user.isPresent()) {
                user.get().setRole(Role.ADMIN);
                userDAO.update(user.get());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> backVisitorRole(List<Long> ids) {
        List<User> admins = new ArrayList<User>();
        ids.stream().forEach(i ->
        {
            if (changeToVisitorRole(i).isPresent()) {
                admins.add(changeToVisitorRole(i).get());
            }
        });
        return admins;
    }

    private Optional<User> changeToVisitorRole(Long id) {
        Optional<User> user = null;
        try {
            user = userDAO.get(id);
            if (user.isPresent()) {
                user.get().setRole(Role.VISITOR);
                userDAO.update(user.get());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
