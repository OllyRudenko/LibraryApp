package ua.olharudenko.libraryapp.web.command.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.dao.BookDAOImpl;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ViewAllBooksCommand extends Command {
    private final Logger logger = LogManager.getLogger(ViewAllBooksCommand.class);

    BookDAOImpl bookDAO = new BookDAOImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Role role = Role.valueOf((String) request.getSession().getAttribute("userRole"));
        Long userId = (Long) request.getSession().getAttribute("userId");

        String errorMessage = null;
        String forward = "templates/error.jsp";

        List<Book> books = null;
        try {
            books = bookDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (books.size() == 0) {
            errorMessage = "Book List is empty";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        } else {
            request.getSession().setAttribute("books", books);
            request.getSession().setAttribute("userRole", role.toString());
            request.getSession().setAttribute("userId", userId);
            request.getSession().setAttribute("userLocale", Locale.EN.toString());

            if (role.equals(Role.ADMIN) || role.equals(Role.LIBRARIAN))
                request.getSession().setAttribute("localesEnum", Locale.values());
            forward = "templates/book/all_books_edit.jsp";
            if (role.equals(Role.VISITOR))
                forward = "templates/book/all_books.jsp";
        }
        return forward;
    }
}
