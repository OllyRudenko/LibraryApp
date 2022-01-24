package ua.olharudenko.libraryapp.web.command.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.dao.UserDAOImpl;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class UpdateUserCommand extends Command {
    private final Logger logger = LogManager.getLogger(UpdateUserCommand.class);

    UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {

        var id = Long.valueOf(request.getParameter("id"));
        var firstName = request.getParameter("firstName");
        var lastName = request.getParameter("lastName");
        var email = request.getParameter("email");
        var password = request.getParameter("password");
        var phone = request.getParameter("phone");
        var adress = request.getParameter("adress");
        var role = Role.valueOf(request.getParameter("userRole"));

        var user = new User(firstName, lastName, role, email, password, phone, adress);
        user.setId(id);

        String errorMessage = null;
        var forward = "templates/error.jsp";

        userDAO.update(user);
        Optional<User> updatedUser = userDAO.get(id);

        if (updatedUser.isEmpty()) {
            errorMessage = "Cannot update user, please try againe";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        } else {
            Role userRole = updatedUser.get().getRole();

            if (userRole == Role.ADMIN)
                forward = "templates/user/admin_profile.jsp";

            if (userRole == Role.LIBRARIAN)
                forward = "templates/user/librarian_profile.jsp";

            if (userRole == Role.VISITOR)
                forward = "templates/user/visitor_profile.jsp";

            request.getSession().setAttribute("user", updatedUser.get());
            request.getSession().setAttribute("userLocale", Locale.EN.toString());
            request.getSession().setAttribute("userRole", role);
        }

        return forward;
    }
}
