package ua.olharudenko.libraryapp.web.command.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.service.impl.AdminServiceImpl;
import ua.olharudenko.libraryapp.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeUserRoleCommand extends Command {
    private final Logger logger = LogManager.getLogger(ChangeUserRoleCommand.class);

    AdminServiceImpl adminService = new AdminServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {

        List<Long> librarianIds = converver(request.getParameterValues("idForLibrarian"));
        List<Long> adminIds = converver(request.getParameterValues("idForAdmin"));
        List<Long> visitorIds = converver(request.getParameterValues("idForVisitor"));
        Long userId = (Long) request.getSession().getAttribute("userId");
        Role userRole = Role.valueOf((String) request.getSession().getAttribute("userRole"));

        String errorMessage = null;
        String forward = "templates/error.jsp";

        if (userRole.equals(Role.VISITOR)) {
            errorMessage = "User role didn't change";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        } else if (userRole.equals(Role.ADMIN)) {
            List<User> users = new ArrayList<>();
            if(librarianIds.size() != 0) {
                List<User> librarians = adminService.createLibrarian(librarianIds);
                users.addAll(librarians);
            }
            if(visitorIds.size() != 0) {
                List<User> visitors = adminService.backVisitorRole(visitorIds);
                users.addAll(visitors);
            }
            if(adminIds.size() != 0) {
                List<User> admins = adminService.confirmNewAdmin(adminIds);
                users.addAll(admins);
            }

            request.getSession().setAttribute("users", users);
            request.getSession().setAttribute("userId", userId.toString());
            request.getSession().setAttribute("userRole", userRole.toString());
            request.getSession().setAttribute("userLocale", Locale.EN.toString());

            forward = "templates/user/all_users.jsp";
        }
        return forward;
    }

    private List<Long> converver(String[] ids) {
        var orderIds = new ArrayList<Long>();
        if (ids != null) {
            Arrays.stream(ids).forEach(i -> orderIds.add(Long.valueOf(i)));
        }
        return orderIds;
    }
}
