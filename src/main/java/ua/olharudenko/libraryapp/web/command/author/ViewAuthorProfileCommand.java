package ua.olharudenko.libraryapp.web.command.author;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.dao.LocalizedAuthorDAOImpl;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.models.LocalizedAuthor;
import ua.olharudenko.libraryapp.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ViewAuthorProfileCommand extends Command {
    private final Logger logger = LogManager.getLogger(ViewAuthorProfileCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        Long authorId = Long.valueOf(request.getParameter("id"));
        Locale local = Locale.valueOf(request.getParameter("locale"));

        String errorMessage = null;
        String forward = "templates/error.jsp";

        Optional<LocalizedAuthor> author = new LocalizedAuthorDAOImpl().get(authorId, local);

        if (author.isEmpty()) {
            errorMessage = "Author isn't exsist";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        }

        request.getSession().setAttribute("author", author.get());
        forward = "templates/author/author_profile.jsp";

        return forward;
    }
}
