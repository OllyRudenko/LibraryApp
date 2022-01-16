package ua.olharudenko.libraryapp.web.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.service.VisitorService;
import ua.olharudenko.libraryapp.service.impl.VisitorServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationCommand extends Command {
    private final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    VisitorService visitorService = new VisitorServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {

        String errorMessage = null;
        var forward = "templates/error.jsp";

        var user = new User();

        Map<String, String> errors = new HashMap<>();
        validationUserFields(request, errors, user);

        if (!errors.isEmpty()) {
            request.setAttribute("errorUser", user);
            request.setAttribute("errors", errors);
            forward = "templates/registration.html";
        } else {

            var registeredUser = visitorService.registration(user);

            if (registeredUser == null) {
                errorMessage = "Cannot registered user, please try againe";
                request.getSession().setAttribute("errorMessage", errorMessage);
                logger.info("errorMessage: " + errorMessage);
                return forward;
            } else {
                var userRole = registeredUser.getRole();

                if (userRole == Role.VISITOR) {
                    forward = "templates/user/visitor_profile.jsp";
                }

                request.getSession().removeAttribute("orders");
                request.getSession().setAttribute("user", registeredUser);
                request.getSession().setAttribute("userLocale", Locale.EN.toString());
            }
        }
        return forward;
    }

    private void validationUserFields(HttpServletRequest request, Map<String, String> errors, User user) {
        var firstName = request.getParameter("firstName");
        if (firstName.length() == 0) {
            errors.put("firstName", "First Name cannot be blank and should be less than 100 simbols");
        } else {
            user.setFirstName(firstName);
        }

        var lastName = request.getParameter("lastName");
        if (lastName.length() == 0) {
            errors.put("lastName", "Last Name cannot be blank and should be less than 100 simbols");
        } else {
            user.setLastName(lastName);
        }

        var email = request.getParameter("email");
        if (lastName.length() == 0) {
            errors.put("email", "email cannot be blank");
        } else if (visitorService.isExistEmail(email)) {
            errors.put("email", "User with this e-mail already exist");
        } else {
            user.setEmail(email);
        }

        var password = request.getParameter("pwd1");
        if (password.length() == 0) {
            errors.put("password", "password cannot be blank");
        } else {
            user.setPassword(password);
        }

        var phone = request.getParameter("phone");
        if (phone.length() == 0) {
            errors.put("phone", "phone cannot be blank");
        } else {
            user.setPhone(phone);
        }

        var adress = request.getParameter("adress");
        if (adress.length() == 0) {
            errors.put("adress", "adress cannot be blank");
        } else {
            user.setAdress(adress);
        }

        user.setRole(Role.VISITOR);
    }
}
