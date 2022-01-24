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
import java.util.List;

public class ViewAllUsersCommand extends Command {
    private final Logger logger = LogManager.getLogger(ViewAllUsersCommand.class);

    UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var role = Role.valueOf(request.getParameter("userRole"));

        String errorMessage = null;
        var forward = "templates/error.jsp";

        List<User> users = null;

        try {
            users = userDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (users.size() == 0) {
            errorMessage = "User List is empty";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        } else {
            if (role == Role.ADMIN)
                forward = "templates/user/all_users.jsp";
            else {
                errorMessage = "Only ADMIN can get information about users";
            }
            request.getSession().setAttribute("users", users);
            request.getSession().setAttribute("userRole", role.toString());
            request.getSession().setAttribute("userLocale", Locale.EN.toString());
        }
        return forward;
    }
}
