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
import java.util.List;

public class ViewAllAuthorsCommand extends Command {
    private final Logger logger = LogManager.getLogger(ViewAllAuthorsCommand.class);

    LocalizedAuthorDAOImpl localizedAuthorDAO = new LocalizedAuthorDAOImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var role = Role.valueOf((String) request.getSession().getAttribute("userRole"));
        var userId = (Long)request.getSession().getAttribute("userId");

        String errorMessage = null;
        String forward = "templates/error.jsp";

        List<LocalizedAuthor> authors = null;
        try {
            authors = localizedAuthorDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (authors.size() == 0) {
            errorMessage = "Author List is empty";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        } else {
            request.getSession().setAttribute("authors", authors);
            request.getSession().setAttribute("userRole", role.toString());
            request.getSession().setAttribute("userId", userId);
            request.getSession().setAttribute("userLocale", Locale.EN.toString());

            if (role.equals(Role.VISITOR)) {
                forward = "templates/author/all_authors.jsp";
            } else {
                forward = "templates/author/all_authors_edit.jsp";
            }
        }
        return forward;
    }
}
