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
import java.util.Optional;

public class DeleteBookCommand extends Command {
    private final Logger logger = LogManager.getLogger(DeleteBookCommand.class);

    BookDAOImpl bookDAO = new BookDAOImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        Role role = Role.valueOf((String) request.getSession().getAttribute("userRole"));
        var id = Long.valueOf(request.getParameter("id"));
//        var locale = Locale.valueOf(request.getParameter("locale"));

        String errorMessage = "DELETING FAILED";
        String forward = "templates/error.jsp";

        if (role.equals(Role.ADMIN) || role.equals(Role.LIBRARIAN)) {
            Optional<Book> bookForDelete = bookDAO.get(id);
            if (bookForDelete.isPresent()) {
                bookDAO.delete(bookForDelete.get());
                request.getSession().setAttribute("userRole", role.toString());
                forward = "/controller?command=viewAllBooks";
            } else {
                request.getSession().setAttribute("errorMessage", errorMessage);
            }
        }
        return forward;
    }
}
