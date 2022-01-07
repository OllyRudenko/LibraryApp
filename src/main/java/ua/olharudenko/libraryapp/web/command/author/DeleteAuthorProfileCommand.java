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
import java.util.Optional;

public class DeleteAuthorProfileCommand extends Command {
    private final Logger logger = LogManager.getLogger(DeleteAuthorProfileCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var role = Role.valueOf(request.getParameter("userRole"));
        var id = Long.valueOf(request.getParameter("id"));
        var local = Locale.valueOf(request.getParameter("locale"));
        var userId = Long.valueOf(request.getParameter("userId"));

        String errorMessage = "DELETING FAILED";
        String forward = "templates/error.jsp";

        Optional<LocalizedAuthor> authorForDelete = new LocalizedAuthorDAOImpl().get(id, local);
        if (!authorForDelete.isEmpty()) {
            new LocalizedAuthorDAOImpl().delete(authorForDelete.get());
            request.getSession().setAttribute("userRole", role.toString());
            request.getSession().setAttribute("userId", userId);
            forward = "/controller?command=viewAllAuthors&role=".concat(role.name());
        }
        return forward;
    }
}
