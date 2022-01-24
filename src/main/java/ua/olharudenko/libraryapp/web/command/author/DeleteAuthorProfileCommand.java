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

    LocalizedAuthorDAOImpl localizedAuthorDAO = new LocalizedAuthorDAOImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var role = Role.valueOf((String) request.getSession().getAttribute("userRole"));
        var id = Long.valueOf(request.getParameter("id"));
        var local = Locale.valueOf(request.getParameter("locale"));
        var userId = (Long) request.getSession().getAttribute("userId");

        String errorMessage = "DELETING FAILED";
        String forward = "templates/error.jsp";

        if(role.equals(Role.ADMIN) || role.equals(Role.LIBRARIAN)) {
            Optional<LocalizedAuthor> authorForDelete = localizedAuthorDAO.get(id, local);
            if (!authorForDelete.isEmpty()) {
                localizedAuthorDAO.delete(authorForDelete.get());
                request.getSession().setAttribute("userRole", role.toString());
                request.getSession().setAttribute("userId", userId);
                forward = "/controller?command=viewAllAuthors&role=".concat(role.name());
            }
        }else{
            request.getSession().setAttribute("errorMessage", errorMessage);
        }
        return forward;
    }
}
