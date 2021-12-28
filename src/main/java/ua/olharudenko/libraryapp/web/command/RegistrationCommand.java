package ua.olharudenko.libraryapp.web.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.dao.UserDAOImpl;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class RegistrationCommand extends Command {
    private final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        HttpSession session = request.getSession();

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("pwd1");
        String phone = request.getParameter("phone");
        String adress = request.getParameter("adress");

        User user = new User(firstName, lastName, Role.VISITOR, email, password, phone, adress);

        // error handler
        String errorMessage = null;
        String forward = "templates/error.jsp";

        User registeredUser = new UserDAOImpl().save(user);

        if (registeredUser == null) {
            errorMessage = "Cannot registered user, please try againe";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        } else {
            Role userRole =registeredUser.getRole();

            if (userRole == Role.VISITOR)
                forward = "templates/user/visitor_profile.jsp";

            request.getSession().setAttribute("user", registeredUser);
            request.getSession().setAttribute("userLocale", Locale.EN.toString());
        }

        return forward;
    }
}
