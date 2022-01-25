package ua.olharudenko.libraryapp.web.command.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.Book;
import ua.olharudenko.libraryapp.service.VisitorService;
import ua.olharudenko.libraryapp.service.impl.VisitorServiceImpl;
import ua.olharudenko.libraryapp.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FindBySearchWordsCommand extends Command {
    private final Logger logger = LogManager.getLogger(FindBySearchWordsCommand.class);

    VisitorService visitorService = new VisitorServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var role = Role.valueOf(request.getParameter("userRole"));
        var searchWord = request.getParameter("searchWords");

        String errorMessage = null;
        String forward = "templates/error.jsp";

        List<Book> books = null;
        try {
            books = visitorService.findBySearchWords(searchWord);
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

            if (role.equals(Role.ADMIN) || role.equals(Role.LIBRARIAN))
                request.getSession().setAttribute("localesEnum", Locale.values());
            forward = "templates/book/all_books_edit.jsp";
            if (role.equals(Role.VISITOR))
                forward = "templates/book/all_books.jsp";
        }
        return forward;
    }
}
