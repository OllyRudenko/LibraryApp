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

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {

        Long id = Long.valueOf(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String adress = request.getParameter("adress");
        Role role = Role.valueOf(request.getParameter("role"));

        User user = new User(firstName, lastName, role, email, password, phone, adress);
        user.setId(id);

        // error handler
        String errorMessage = null;
        String forward = "templates/error.jsp";

        new UserDAOImpl().update(user);
        Optional<User> updatedUser = new UserDAOImpl().get(id);

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
        }

        return forward;
    }
}
