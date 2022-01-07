package ua.olharudenko.libraryapp.web.command.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.olharudenko.libraryapp.dao.OrderDaoImpl;
import ua.olharudenko.libraryapp.dao.UserDAOImpl;
import ua.olharudenko.libraryapp.enums.Role;
import ua.olharudenko.libraryapp.models.Order;
import ua.olharudenko.libraryapp.models.User;
import ua.olharudenko.libraryapp.service.impl.OrderServiceImpl;
import ua.olharudenko.libraryapp.web.command.Command;
import ua.olharudenko.libraryapp.web.command.user.DeleteUserCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class DeleteOrderCommand extends Command {
    private final Logger logger = LogManager.getLogger(DeleteOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        var role = Role.valueOf(request.getParameter("userRole"));
        var orderId = Long.valueOf(request.getParameter("id"));
        var userId = Long.valueOf(request.getParameter("userId"));

        String errorMessage = "DELETING FAILED";
        String forward = "templates/error.jsp";

        Optional<Order> orderForDelete = new OrderDaoImpl().get(orderId);
        if (!orderForDelete.isEmpty()) {
            boolean result = new OrderServiceImpl().delete(orderForDelete.get(), userId);
            request.getSession().setAttribute("userRole", role);
            request.getSession().setAttribute("userId", userId);
            forward = "/controller?command=viewAllOrders";
            if(!result) {
                errorMessage = "Status your order confirmed by Admin. You cannot delete it";
                request.getSession().setAttribute("errorMessage", errorMessage);
            }
        }

        // todo how make alert window
//        <script type='text/javascript'>
//                var errorMessage = '<%=(String)Session["errorMessage"]%>';
//        alert(errorMessage);
//        </script>
        return forward;
    }
}
