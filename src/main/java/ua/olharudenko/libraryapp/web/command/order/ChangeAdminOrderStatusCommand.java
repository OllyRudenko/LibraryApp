package ua.olharudenko.libraryapp.web.command.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.Order;
import ua.olharudenko.libraryapp.service.impl.OrderServiceImpl;
import ua.olharudenko.libraryapp.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeAdminOrderStatusCommand extends Command {
    private final Logger logger = LogManager.getLogger(ChangeAdminOrderStatusCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var orderService = new OrderServiceImpl();

        List<Long> orderIds = converver(request.getParameterValues("id"));
        var userId = Long.valueOf(request.getParameter("userId"));
        var userRole = Role.valueOf(request.getParameter("userRole"));

        String errorMessage = null;
        String forward = "templates/error.jsp";

        if (userRole.equals(Role.VISITOR)) {
            errorMessage = "Order status didn't change";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        } else if (userRole.equals(Role.ADMIN) || userRole.equals(Role.LIBRARIAN)){
            List<Order> orders = orderService.changeAdminOrderStatus(orderIds, userRole);
            request.getSession().setAttribute("orders", orders);
            request.getSession().setAttribute("userId", userId.toString());
            request.getSession().setAttribute("userRole", userRole.toString());
            request.getSession().setAttribute("userLocale", Locale.EN.toString());

            forward = "templates/order/all_orders_edit.jsp";
        }
        return forward;
    }

    private List<Long> converver(String[] ids) {
        var orderIds = new ArrayList<Long>();
        Arrays.stream(ids).forEach(i -> orderIds.add(Long.valueOf(i)));
        return orderIds;
    }
}