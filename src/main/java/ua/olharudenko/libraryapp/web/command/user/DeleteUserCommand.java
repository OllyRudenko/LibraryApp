package ua.olharudenko.libraryapp.web.command.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.dao.UserDAOImpl;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class DeleteUserCommand extends Command {
    private final Logger logger = LogManager.getLogger(DeleteUserCommand.class);

    UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var role = Role.valueOf(request.getParameter("userRole"));
        var id = Long.valueOf(request.getParameter("id"));

        String errorMessage = "DELETING FAILED";
        var forward = "templates/error.jsp";

        Optional<User> userForDelete = userDAO.get(id);
        if (!userForDelete.isEmpty()) {
            userDAO.delete(userForDelete.get());
            request.getSession().setAttribute("role", role);
            forward = "/controller?command=viewAllUsers&role=ADMIN";
        }
        return forward;
    }
}