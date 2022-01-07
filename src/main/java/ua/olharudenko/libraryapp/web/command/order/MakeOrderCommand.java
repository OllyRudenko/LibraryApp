package ua.olharudenko.libraryapp.web.command.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.enums.Locale;
import ua.olharudenko.libraryapp.enums.OrderStatus;
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

public class MakeOrderCommand extends Command {
    private final Logger logger = LogManager.getLogger(MakeOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var orderService = new OrderServiceImpl();

        List<Long> bookIds = converver(request.getParameterValues("id"));
        var userId = Long.valueOf(request.getParameter("userId"));
        var userRole = Role.valueOf(request.getParameter("userRole"));
        int quantity = 1;

        String errorMessage = null;
        var forward = "templates/error.jsp";

        List<Order> orders = orderService.makeOrder(userId, bookIds, OrderStatus.SUBSCRIPTION, quantity);

        if (orders.size() == 0) {
            errorMessage = "Order List is empty";
            request.getSession().setAttribute("errorMessage", errorMessage);
            logger.info("errorMessage: " + errorMessage);
            return forward;
        } else {
            request.getSession().setAttribute("orders", orders);
            request.getSession().setAttribute("userId", userId.toString());
            request.getSession().setAttribute("userRole", userRole.toString());
            request.getSession().setAttribute("userLocale", Locale.EN.toString());

            forward = "templates/order/visitor_orders.jsp";
        }
        return forward;
    }

    private List<Long> converver(String[] ids){
         var bookIds = new ArrayList<Long>();
         Arrays.stream(ids).forEach(i -> bookIds.add(Long.valueOf(i)));
        return bookIds;
    }
}
