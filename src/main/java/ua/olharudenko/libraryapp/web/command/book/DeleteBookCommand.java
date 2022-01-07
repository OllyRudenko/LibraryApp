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

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var role = Role.valueOf(request.getParameter("userRole"));
        var id = Long.valueOf(request.getParameter("id"));
        var local = Locale.valueOf(request.getParameter("local"));

        String errorMessage = "DELETING FAILED";
        String forward = "templates/error.jsp";

        if (role.equals(Role.ADMIN) || role.equals(Role.LIBRARIAN)) {
            Optional<Book> bookForDelete = new BookDAOImpl().get(id);
            if (!bookForDelete.isEmpty()) {
                new BookDAOImpl().delete(bookForDelete.get());
                request.getSession().setAttribute("userRole", role);
                forward = "/controller?command=viewAllBooks&role=".concat(role.name());
            }
        }
        return forward;
    }
}
