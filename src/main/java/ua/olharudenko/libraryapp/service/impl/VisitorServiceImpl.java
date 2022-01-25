package ua.olharudenko.libraryapp.service.impl;

import org.apache.commons.lang3.StringUtils;
import ua.olharudenko.libraryapp.dao.BookDAOImpl;
import ua.olharudenko.libraryapp.dao.UserDAOImpl;
import ua.olharudenko.libraryapp.enums.OrderStatus;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.service.VisitorService;

import java.awt.print.Pageable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class VisitorServiceImpl implements VisitorService {
    UserDAOImpl userDAO = new UserDAOImpl();

    BookDAOImpl bookDAO = new BookDAOImpl();

    @Override
    public User registration(User user) {
        var registeredUser = new User();
        registeredUser = userDAO.save(user);
        return registeredUser;
    }

    @Override
    public boolean isExistEmail(String email) {
        Optional<User> user = Optional.of(new User());
        try {
            user = userDAO.findUserByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user.isPresent()) {
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

    @Override
    public List<Book> findBySearchWords(String searchWords) throws SQLException {

        List<Book> booksFindedBySearchWords = new ArrayList<Book>();

        String delimeter = " ";
        String[] subStr = searchWords.toLowerCase().split(delimeter);

        String patternString = "\\b(" + StringUtils.join(subStr, "|") + ")\\b";
        Pattern pattern = Pattern.compile(patternString);

        bookDAO.getAll().stream().forEach(n -> {
            if (pattern.matcher(n.getTitle().toLowerCase()).find()
                    || pattern.matcher(n.getLocalizedAuthor().getFullName().toLowerCase()).find()) {
                booksFindedBySearchWords.add(n);
            }
        });

        return booksFindedBySearchWords;
    }
}
