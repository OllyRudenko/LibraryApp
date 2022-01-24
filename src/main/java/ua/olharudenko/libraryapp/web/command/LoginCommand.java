package ua.olharudenko.libraryapp.web.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.dao.UserDAOImpl;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.Order;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LoginCommand extends Command {

    private final Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        HttpSession session = request.getSession();

        var email = request.getParameter("email");
        var password = request.getParameter("password");

        Locale userLocale = Locale.valueOf((String) session.getAttribute("locale"));

        System.out.println(userLocale.toString() + "!!!!!!!!!!!!!!!!!!! ");

        logger.info("get email and password from request");

        String errorMessage = null;
        var forward = "templates/error.jsp";

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        }

        Optional<User> user = new UserDAOImpl().findUserByEmail(email);

        if (user.isEmpty() || !password.equals(user.get().getPassword())) {
            errorMessage = "Cannot find user with such email/password";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        } else {
            Role userRole = user.get().getRole();

            if (userRole == Role.ADMIN)
                forward = "templates/user/admin_profile.jsp";

            if (userRole == Role.LIBRARIAN)
                forward = "templates/user/librarian_profile.jsp";

            if (userRole == Role.VISITOR) {
                getAllVisitorOrders(user.get().getId(), userRole, request, userLocale);
                forward = "templates/user/visitor_profile.jsp";
            }

            request.getSession().setAttribute("user", user.get());
            request.getSession().setAttribute("userId", user.get().getId());

            request.getSession().setAttribute("userRole", userRole.toString());
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocale.toString());
            //request.getSession().setAttribute("locale", userLocale.toString());
        }

        return forward;
    }

    private List<Order> getAllVisitorOrders(Long userId, Role userRole, HttpServletRequest request, Locale userLocale) throws SQLException {
        List<Order> orders = new OrderServiceImpl().getAllOrders(userId, userRole);

        if (!(orders.size() == 0)) {
            request.getSession().setAttribute("orders", orders);
            request.getSession().setAttribute("userRole", userRole.toString());
            request.getSession().setAttribute("userId", userId);
            Config.set(request.getSession(), "javax.servlet.jsp.jstl.fmt.locale", userLocale.toString());
            //request.getSession().setAttribute("locale", Locale.EN.toString());
            request.getSession().setAttribute("locale", request.getSession().getAttribute("language"));
            }

        return orders;
    }

//
//<!-- <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
//<fmt:setLocale value="${language}"/>
//<fmt:setBundle basename="i18n.lang"/>
//            -->
}

