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
import java.util.HashMap;
import java.util.Map;

public class AddNewAuthorCommand extends Command {
    private final Logger logger = LogManager.getLogger(AddNewAuthorCommand.class);

    LocalizedAuthorDAOImpl localizedAuthorDAO = new LocalizedAuthorDAOImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        Role userRole = Role.valueOf((String) request.getSession().getAttribute("userRole"));
//        Locale local = Locale.valueOf(request.getParameter("local"));

        String errorMessage = null;
        String forward = "templates/error.jsp";

        var author = new LocalizedAuthor();
        var savedAuthor = new LocalizedAuthor();

        if (userRole == Role.ADMIN || userRole == Role.LIBRARIAN) {
            Map<String, String> errors = new HashMap<>();
            validationLocalizedAuthorFields(request, errors, author);

            if (!errors.isEmpty()) {
                request.setAttribute("author", author);
                request.setAttribute("errors", errors);
                forward = "templates/author/all_authors_edit.jsp";
            } else {
                savedAuthor = localizedAuthorDAO.save(author);
                if (savedAuthor == null) {
                    errorMessage = "Cannot add author, please try againe";
                    request.getSession().setAttribute("errorMessage", errorMessage);
                    logger.info("errorMessage: " + errorMessage);
                    return forward;
                }

                request.getSession().setAttribute("author", savedAuthor);
                request.getSession().setAttribute("userRole", userRole.toString());
                request.getSession().setAttribute("userLocale", Locale.EN.toString());
                forward = "templates/author/author_profile_edit.jsp";
            }
        }
        return forward;
    }

    private void validationLocalizedAuthorFields(HttpServletRequest request, Map<String, String> errors, LocalizedAuthor author) {
        var fullName = request.getParameter("fullName");
        if (fullName.length() == 0) {
            errors.put("fullName", "Full Name cannot be blank and should be less than 200 simbols");
        } else {
            author.setFullName(fullName);
        }

        var biografy = request.getParameter("biografy");
        if (biografy.length() == 0) {
            errors.put("biografy", "Biography cannot be blank and should be less than 1200 simbols");
        } else {
            author.setBiografy(biografy);
        }

        var local = request.getParameter("local");
        if (local.length() == 0) {
            errors.put("local", "Locale cannot be blank");
        } else {
            author.setLocal(Locale.valueOf(local));
        }
    }
}
