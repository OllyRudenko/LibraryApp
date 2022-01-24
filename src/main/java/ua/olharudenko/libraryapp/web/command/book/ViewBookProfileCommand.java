package ua.olharudenko.libraryapp.web.command.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.dao.BookDAOImpl;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ViewBookProfileCommand extends Command {
    private final Logger logger = LogManager.getLogger(ViewBookProfileCommand.class);

    BookDAOImpl bookDAO = new BookDAOImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        HttpSession session = request.getSession();

        var id = Long.valueOf(request.getParameter("id"));
        Role role = Role.valueOf((String) session.getAttribute("userRole"));

        String errorMessage = null;
        String forward = "templates/error.jsp";

        Optional<Book> book = bookDAO.get(id);

        if (book.isEmpty()) {
            errorMessage = "Book isn't exsist";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        }

        request.getSession().setAttribute("book", book.get());
        request.getSession().setAttribute("userRole", role.toString());
        if(role.equals(Role.VISITOR))
        forward = "templates/book/book_profile.jsp";
        if(role.equals(Role.ADMIN) || role.equals(Role.LIBRARIAN))
        forward = "templates/book/book_profile_edit.jsp";

        return forward;
    }
}
