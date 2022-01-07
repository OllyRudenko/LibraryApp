package ua.olharudenko.libraryapp.web.command.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.Order;
import ua.olharudenko.libraryapp.service.impl.OrderServiceImpl;
import ua.olharudenko.libraryapp.web.command.Command;
import ua.olharudenko.libraryapp.web.command.user.ViewAllUsersCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ViewAllOrdersCommand extends Command {
    private final Logger logger = LogManager.getLogger(ViewAllOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var orderService = new OrderServiceImpl();

        var role = Role.valueOf(request.getParameter("userRole"));
        Long userId = Long.valueOf(request.getParameter("userId"));

        String errorMessage = null;
        var forward = "templates/error.jsp";

        List<Order> orders = orderService.getAllOrders(userId, role);

        if (orders.size() == 0) {
            errorMessage = "Order List is empty";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        } else {
            request.getSession().setAttribute("orders", orders);
            request.getSession().setAttribute("userRole", role.toString());
            request.getSession().setAttribute("userId", userId);
            request.getSession().setAttribute("userLocale", Locale.EN.toString());
            if (role == Role.ADMIN || role == Role.LIBRARIAN)
                forward = "templates/order/all_orders_edit.jsp";
            else {
                forward = "templates/order/visitor_orders.jsp";
            }
        }
        return forward;
    }
}
