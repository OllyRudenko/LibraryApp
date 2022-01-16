package ua.olharudenko.libraryapp.service.impl;

import ua.olharudenko.libraryapp.dao.UserDAOImpl;
import ua.olharudenko.libraryapp.enums.OrderStatus;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.service.VisitorService;

import java.sql.SQLException;
import java.util.Optional;

public class VisitorServiceImpl implements VisitorService {
    UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public User registration(User user) {
        var registeredUser = new User();
        registeredUser = userDAO.save(user);
        return registeredUser;
    }

    @Override
    public boolean isExistEmail(String email){
        Optional<User> user = Optional.of(new User());
        try {
            user = userDAO.findUserByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public Optional<Book> findByAuthor() {
        return Optional.empty();
    }

    @Override
    public Optional<Book> findByTitle() {
        return Optional.empty();
    }

    @Override
    public void makeOrder(OrderStatus orderStatus) {

    }
}
