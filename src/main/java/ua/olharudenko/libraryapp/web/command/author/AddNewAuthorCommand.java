package ua.olharudenko.libraryapp.web.command.author;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.dao.LocalizedAuthorDAOImpl;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;
import ua.olharudenko.libraryapp.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AddNewAuthorCommand extends Command {
    private final Logger logger = LogManager.getLogger(AddNewAuthorCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var userRole = Role.valueOf(request.getParameter("userRole"));
        var fullName = request.getParameter("fullName");
        var biografy = request.getParameter("biografy");
        var local = Locale.valueOf(request.getParameter("local"));

        String errorMessage = null;
        String forward = "templates/error.jsp";

        var author = new LocalizedAuthor();

        if (userRole == Role.ADMIN || userRole == Role.LIBRARIAN) {
            author = new LocalizedAuthorDAOImpl().save(new LocalizedAuthor(fullName, local, biografy, null));
            if (author == null) {
                errorMessage = "Cannot add author, please try againe";
                request.getSession().setAttribute("errorMessage", errorMessage);
                logger.info("errorMessage: " + errorMessage);
                return forward;
            }
        }
        request.getSession().setAttribute("author", author);
        request.getSession().setAttribute("userRole", userRole.toString());
        request.getSession().setAttribute("userLocale", Locale.EN.toString());
        forward = "templates/author/author_profile_edit.jsp";

        return forward;
    }
}
